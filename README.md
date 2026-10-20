# Reading list: Kotlin Multiplatform, Android, iOS, desktop, web

The app built through the Kotlin Multiplatform chapter of <https://fauzisho.github.io/notes/>, in its final version:
one Compose Multiplatform UI, a Room database on four platforms, Navigation 3, offline sync with a small Ktor server
that shares its merge code (`:sync`) with the app.

| Module | What it is |
|---|---|
| `shared` | the app: Android, iOS, desktop (JVM) and web (Kotlin/Wasm) |
| `androidApp` | the Android entry point |
| `sync` | the sync format, the merge rule and the clock, shared by the app and the server |
| `server` | the sync server (Ktor, JVM) |

```bash
./gradlew :sync:jvmTest :shared:jvmTest      # merge tests, and seven sync stories against the real server
./gradlew :server:run                        # the server, on port 8080
./gradlew :shared:run                        # the desktop app
./gradlew -Pkmp.ios=true :shared:linkDebugFrameworkIosSimulatorArm64   # iOS (needs Xcode)
```

The CI workflow in `.github/workflows/ci.yml` compiles every target, runs the tests on the JVM, in Chrome, on an
Android emulator and on the iOS simulator.
