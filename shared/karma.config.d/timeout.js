// The timing test runs six rounds against SQLite in a web worker; Mocha's default 2-second limit is too short.
config.set({ client: { mocha: { timeout: 60000 } } });
