# Tech Smart Studio — Android App

This is the Android WebView wrapper for **Tech Smart Studio**, an AI text-to-video generator.

**Web App URL:** https://tech-smart-studio-terrancemonico92573207.adaptive.ai  
**Package:** `com.techsmartstudio.app`  
**Min Android:** 5.0 (API 21)  
**Target Android:** 14 (API 34)

---

## How to Build the APK

### Option A — Android Studio (Recommended, Easiest)

1. **Download and install [Android Studio](https://developer.android.com/studio)**
2. Open Android Studio → **Open** → select this folder (`tech-smart-studio-android/`)
3. Wait for Gradle sync to complete (first run downloads dependencies — may take a few minutes)
4. Build the APK:
   - Menu → **Build → Build Bundle(s) / APK(s) → Build APK(s)**
   - The APK will be at: `app/build/outputs/apk/debug/app-debug.apk`
5. To install on a connected device: **Run → Run 'app'** (or drag the APK to your device)

### Option B — Command Line (requires JDK 17+)

```bash
# macOS/Linux
./gradlew assembleDebug

# Windows
gradlew.bat assembleDebug
```

Output: `app/build/outputs/apk/debug/app-debug.apk`

### Option C — Online Build Service (No Local Tools)

Use **[Appetize.io](https://appetize.io)** or **[Buildozer](https://buildozer.readthedocs.io)** to build in the cloud.

---

## Installing the APK on Android

1. Transfer the APK file to your Android device (via USB, Google Drive, or email)
2. On your device: **Settings → Security → Install Unknown Apps** → enable for your file manager
3. Open the APK file and tap **Install**

---

## Features

- Full WebView wrapper loading the Tech Smart Studio web app
- JavaScript enabled (required for the AI features)
- LocalStorage, cookies, and DOM storage support
- Camera and microphone permission support
- File upload support
- Back button navigation support
- Hardware accelerated rendering

---

## Customization

To change the app URL, edit `MainActivity.kt`:
```kotlin
private const val APP_URL = "https://tech-smart-studio-terrancemonico92573207.adaptive.ai"
```

To change app name, edit `res/values/strings.xml`:
```xml
<string name="app_name">Tech Smart Studio</string>
```
