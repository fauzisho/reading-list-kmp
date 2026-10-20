// A web worker for androidx.sqlite's WebWorkerSQLiteDriver. It speaks the same message protocol as the
// reference worker in the androidx repository (sqlite/sqlite-web-worker-test/web-worker/worker.js):
//   request {id, data: {cmd: 'open' | 'prepare' | 'step' | 'close', ...}}  ->  reply {id, data} or {id, error}
// The difference is the storage. The reference worker opens databases with SQLite's "opfs" VFS, which needs
// SharedArrayBuffer and therefore a cross-origin-isolated page (COOP/COEP headers). A static host such as
// GitHub Pages cannot send those headers, so this worker uses the "opfs-sahpool" VFS, which needs neither,
// and opens an in-memory database when asked for ':memory:'. The app decides which (see Database.wasmJs.kt): the
// pool's files can be open in one tab only, so a second tab gets an in-memory database and a visible warning.
import sqlite3InitModule from '@sqlite.org/sqlite-wasm';

let sqlite3 = null;
let pool = null;            // the opfs-sahpool VFS, installed on the first request for a file
const databases = new Map();
const statements = new Map();
let nextDatabaseId = 0;
let nextStatementId = 0;

function reply(id, data) { postMessage({ id, data }); }
function fail(id, error) { postMessage({ id, error: String(error && error.message || error) }); }

async function open(id, { fileName }) {
    try {
        let db;
        if (fileName === ':memory:') {
            db = new sqlite3.oo1.DB(':memory:');
        } else {
            pool ??= await sqlite3.installOpfsSAHPoolVfs({ name: 'reading-list' });
            db = new pool.OpfsSAHPoolDb(fileName.startsWith('/') ? fileName : '/' + fileName);
        }
        const databaseId = nextDatabaseId++;
        databases.set(databaseId, db);
        reply(id, { databaseId });
    } catch (e) { fail(id, e); }
}

function prepare(id, { databaseId, sql }) {
    const db = databases.get(databaseId);
    if (!db) return fail(id, 'Invalid database ID: ' + databaseId);
    try {
        const stmt = db.prepare(sql);
        const statementId = nextStatementId++;
        statements.set(statementId, stmt);
        const columnNames = [];
        for (let i = 0; i < stmt.columnCount; i++) columnNames.push(sqlite3.capi.sqlite3_column_name(stmt, i));
        reply(id, { statementId, parameterCount: sqlite3.capi.sqlite3_bind_parameter_count(stmt), columnNames });
    } catch (e) { fail(id, e); }
}

function step(id, { statementId, bindings }) {
    const stmt = statements.get(statementId);
    if (!stmt) return fail(id, 'Invalid statement ID: ' + statementId);
    try {
        const rows = [], columnTypes = [];
        stmt.reset();
        stmt.clearBindings();
        bindings.forEach((value, i) => stmt.bind(i + 1, value));
        while (stmt.step()) {
            if (!columnTypes.length) for (let i = 0; i < stmt.columnCount; i++) columnTypes.push(sqlite3.capi.sqlite3_column_type(stmt, i));
            rows.push(stmt.get([]));
        }
        reply(id, { rows, columnTypes });
    } catch (e) { fail(id, e); }
}

// As in the reference worker, a successful close sends no reply.
function close(id, { statementId, databaseId }) {
    try {
        if (statementId != null) { statements.get(statementId)?.finalize(); statements.delete(statementId); }
        if (databaseId != null) { databases.get(databaseId)?.close(); databases.delete(databaseId); }
    } catch (e) { fail(id, e); }
}

const commands = { open, prepare, step, close };

async function handle(e) {
    const { id, data } = e.data || {};
    const command = data && commands[data.cmd];
    if (!command) return fail(id, "Invalid request: '" + (data && data.cmd) + "'");
    await command(id, data);
}

// One request at a time, in order: 'open' may wait for the pool, and nothing may overtake it.
const ready = sqlite3InitModule().then((instance) => { sqlite3 = instance; });
let chain = ready;
onmessage = (e) => { chain = chain.then(() => handle(e)); };
