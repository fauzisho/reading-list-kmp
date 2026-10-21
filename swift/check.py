# Type-checks every Swift snippet in swift/snippets against two builds of the same Kotlin code: the Objective-C
# framework (linkDebugFrameworkIosSimulatorArm64) and the Swift export (embedSwiftExportForXcode). Writes
# out/results.json with, for each snippet and each export, whether it compiled and the compiler's first error,
# and copies what each export generated (the Objective-C header, the Swift interfaces) into out/.
#   python3 swift/check.py OBJC_FRAMEWORK_DIR SWIFT_EXPORT_DIR OUT
import glob, json, os, shutil, subprocess, sys

objc_dir, swift_dir, out = (os.path.abspath(p) for p in sys.argv[1:4])
os.makedirs(out, exist_ok=True)
sdk = subprocess.run(["xcrun", "--sdk", "iphonesimulator", "--show-sdk-path"], capture_output=True, text=True).stdout.strip()
target = "arm64-apple-ios17.0-simulator"

def swift_search_args():
    """Every directory under the Swift export output that holds a Swift module or a module map."""
    args = []
    for p in sorted(glob.glob(f"{swift_dir}/**/*", recursive=True)):
        if p.endswith(".swiftmodule") or p.endswith(".swiftinterface"):
            args += ["-I", os.path.dirname(p)]
        if p.endswith("module.modulemap"):
            args += ["-I", os.path.dirname(p), "-Xcc", f"-fmodule-map-file={p}"]
        if p.endswith(".framework"):
            args += ["-F", os.path.dirname(p)]
    return list(dict.fromkeys(args))

modes = {"objc": ["-F", objc_dir], "swift-export": swift_search_args()}
index = json.load(open("swift/snippets/index.json"))
results = {}
for path in sorted(glob.glob("swift/snippets/*.swift")):
    name = os.path.basename(path)[:-6]
    results[name] = {"what": index[name]}
    for mode, args in modes.items():
        r = subprocess.run(["xcrun", "swiftc", "-typecheck", "-target", target, "-sdk", sdk, *args, path], capture_output=True, text=True)
        errors = [l.split(": error: ", 1)[1] for l in r.stderr.splitlines() if ": error: " in l]
        results[name][mode] = {"ok": r.returncode == 0, "errors": errors[:3]}
        print(f"{name:24} {mode:12} {'ok' if r.returncode == 0 else 'FAIL: ' + (errors[0] if errors else r.stderr.strip()[:200])}")

json.dump(dict(target=target, search_args=modes, results=results), open(f"{out}/results.json", "w"), indent=1)
# what each export generated
for h in glob.glob(f"{objc_dir}/ReadingKit.framework/Headers/*.h"):
    shutil.copy(h, f"{out}/objc-{os.path.basename(h)}")
for p in glob.glob(f"{swift_dir}/**/*", recursive=True):
    if p.endswith((".swift", ".swiftinterface", ".h", ".modulemap")) and os.path.getsize(p) < 2_000_000:
        shutil.copy(p, f"{out}/swift-export-" + p[len(swift_dir) + 1:].replace("/", "__"))
with open(f"{out}/swift-export-files.txt", "w") as f:
    for p in sorted(glob.glob(f"{swift_dir}/**/*", recursive=True)):
        f.write(f"{os.path.getsize(p) if os.path.isfile(p) else 'dir':>10}  {p[len(swift_dir) + 1:]}\n")
