# MAD204-ASSIGNMENT2--RamandeepSingh(A00194321)

## Project Overview
This is a multi-activity Android application developed in Kotlin, targeting API 30 (Android 11). The app demonstrates advanced UI, navigation, lifecycle logging, persistence with SharedPreferences, RecyclerView with interactive list handling, and theme styling. It contains multiple screens such as login, registration, dashboard, profile, country list, and settings with complete navigation and lifecycle management.

## Features
- Multiple screens including Login, Registration, Dashboard, Profile, Countries List, and Settings.
- Email and password validation on login.
- Registration form with input validation and data persistence.
- Persistent user session using SharedPreferences to remember login details and app settings.
- Dashboard screen welcomes user by name/email and provides navigation buttons.
- Profile screen displays and allows editing of user details with validation.
- Countries list implemented via RecyclerView with click, long-press delete with undo via Snackbar, swipe-to-delete gesture, and alphabetical sorting.
- Settings screen with switches for Dark Mode and Notifications; preferences stored persistently.
- Lifecycle logging (`Log.d`) implemented in Dashboard and Profile to track activity state transitions.
- Consistent UI design with ConstraintLayout, styles.xml, and material themes.
- Back arrow navigation in the app bar to return to Dashboard from all post-login screens.
- Comprehensive inline and file header documentation following best practices.

## Project Structure
- **MainActivity.kt** — Login screen with validation.
- **RegistrationActivity.kt** — Registration form and data persistence.
- **DashboardActivity.kt** — Main hub post-login with lifecycle logging.
- **ProfileActivity.kt** — View and edit user details, persisting changes.
- **ListActivity.kt** — RecyclerView list of countries with interactive features.
- **SettingsActivity.kt** — User preferences with toggles for dark mode and notifications.
- Layout XML files for each activity in `res/layout/`.
- Styled with `styles.xml` and themed with `themes.xml`.
- Colors defined in `colors.xml` matching Material color palettes.

## Technologies Used
- Programming Language: Kotlin
- Target SDK: API 30 (Android 11)
- AndroidX libraries including RecyclerView and Material Components
- SharedPreferences for lightweight data persistence
- ConstraintLayout for responsive UI design
- GitHub for version control with commits documenting feature progress

## How to Run
1. Clone or download the repository to your local machine.
2. Open the project in Android Studio (Electric Eel or later recommended).
3. Make sure your development environment is set to use at least API 30.
4. Build the project and run on an emulator or physical device running Android 11 or later.
5. Use the registration screen to create a new account, then log in.
6. Navigate through the app using buttons on the dashboard and action bar.
7. Test RecyclerView interactions (click, long-press, swipe) in the countries list.
8. Modify settings in the settings screen and restart the app to see persistence.

## Lifecycle Behavior
- DashboardActivity and ProfileActivity override all main lifecycle methods (`onCreate`, `onStart`, `onResume`, `onPause`, `onStop`, `onDestroy`).
- Each lifecycle transition logs a debug message to Logcat for traceability.
- Lifecycle logging helps understand activity state changes during navigation and app use.

## Notes on Validation & Error Handling
- Login form validates that email contains '@' and password length is at least 4.
- Registration form validates non-empty fields, proper email format, and age > 0.
- Profile edit form applies similar validation before saving updates.
- RecyclerView handles empty list gracefully with a message.
- All data persistence uses try-catch around risky operations like string-to-int parsing.

## GitHub Repository
The source code is version-controlled with meaningful commit messages describing each implemented feature including activity setup, validation, RecyclerView implementation, persistence, theme and style application, and lifecycle logging.

## Future Improvements
- Add unit tests and UI automation tests.
- Implement actual theme switching based on Dark Mode toggle.
- Enhance country list with flag images loaded from the web or local resources.
- Add password encryption and authentication backend.
- Improve accessibility compliance.
