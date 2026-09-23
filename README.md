# NEXUS Study Tracker — Android app

A native Android (WebView) wrapper around `study-tracker-pro.html`.
Your HTML lives at `app/src/main/assets/index.html` — replace that file any time and rebuild.

## Get the APK

**Option A — no Android Studio (GitHub builds it for you)**
1. Create a new GitHub repo and upload everything in this folder (keep the `.github` folder).
2. Open the repo's **Actions** tab → **Build APK** → wait ~4 min for the green tick.
3. Open the finished run → **Artifacts** → download **NEXUS-Study-Tracker-APK** → unzip → `app-debug.apk`.

**Option B — Android Studio**
1. File → Open → select this folder, wait for Gradle sync.
2. Build → Build Bundle(s) / APK(s) → Build APK(s).
3. APK is at `app/build/outputs/apk/debug/app-debug.apk`.

## Install
Copy the APK to your phone, tap it, and allow "Install unknown apps" for your file manager/browser when asked.

## Notes
- Data is stored inside the app (localStorage) and stays on the phone. Use the in-app Backup to save a JSON file; it opens Android's "Save as" picker.
- Confirm dialogs (Delete / Reset All) are handled natively.
- PDF export uses jsPDF, which the HTML loads from a CDN. For fully offline PDF export, download
  https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js
  and save it as `app/src/main/assets/jspdf.umd.min.js` — the app will use it automatically.
- Google Fonts also load online; offline the app falls back to system fonts.
- To change the app name, icon or package id: `res/values/strings.xml`, `res/mipmap-*`, `app/build.gradle`.
- Requires Android 7.0+ (API 24). Builds with Android Studio Koala or newer (AGP 8.5.2 / Gradle 8.7 / JDK 17).
