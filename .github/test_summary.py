# Sums the JUnit XML files a test task wrote and reports them as a GitHub annotation and in the job summary,
# so that the counts are visible on the run's page without opening the logs.
#   python3 .github/test_summary.py "label" "glob" ["glob" …]
import glob, os, sys, xml.etree.ElementTree as ET

label, patterns = sys.argv[1], sys.argv[2:]
tests = failures = skipped = 0
suites = set()
for pattern in patterns:
    for path in glob.glob(pattern, recursive=True):
        root = ET.parse(path).getroot()
        for s in ([root] if root.tag == "testsuite" else root.iter("testsuite")):
            tests += int(s.get("tests", 0)); skipped += int(s.get("skipped", 0))
            failures += int(s.get("failures", 0)) + int(s.get("errors", 0))
            suites.add(s.get("name", "").split(".")[-1].split("[")[0])
line = f"{label}: {tests} tests, {failures} failed, {skipped} skipped ({', '.join(sorted(suites)) or 'no results found'})"
print(f"::{'error' if failures or not tests else 'notice'} title={label}::{line}")
if os.environ.get("GITHUB_STEP_SUMMARY"):
    with open(os.environ["GITHUB_STEP_SUMMARY"], "a") as f: f.write(f"- {line}\n")
