# 🔥 Tinder Clone – Modular Android App

A fully modularized Tinder-like dating application built with **Jetpack Compose**, **Kotlin**, **Firebase**, and **Clean Architecture principles**.  
It features user authentication, swiping to match with other users, real-time chat, and profile customization.

---

## 📲 Features

- 🔐 **Onboarding** (Login via Firebase / Profile Creation)
- 🏠 **Home Screen** with swipe-to-match functionality
- 🧑‍💼 **Edit Profile** with image uploads
- 💬 **Chat** with matched users

---

## 🧱 Module Structure

This project follows **Clean Architecture + Modularization**, broken into 3 layers per feature: `feature`, `domain`, and `data`.

- :app → App entry point + dependency injection
- :core:ui → Shared UI styles and components (theme, colors)
- :core:firebase → Shared Firebase logic (auth, firestore, storage)

- :feature:* → UI, ViewModel and components for each feature
- :core:*:domain: → Business logic, use cases, and repository interfaces
- :core:*:data: → Repository implementations and data sources


📦 Each feature is self-contained. Domain defines logic, data implements it, and feature uses it.

---

## 🛠️ Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **Koin** for Dependency Injection
- **Firebase** (Auth, Firestore, Storage)
- **Coroutines** + **Flow**
- **MVVM** + **Clean Architecture**
- **Modularization** (by feature and layer)

---

# Features

## 🔐 Onboarding

<p float="left">
  <img src="https://github.com/alejandro-piguave/TinderCloneCompose/blob/master/screenshots/login_screen.png" width="250">
  <img src="https://github.com/alejandro-piguave/TinderCloneCompose/blob/master/screenshots/create_profile_screen.png" width="250">
  <img src="https://github.com/alejandro-piguave/TinderCloneCompose/blob/master/screenshots/add_picture_screen.png" width="250">
</p>

Only Google Sign in is allowed. Either of these two actions should be performed:
* **If the user has an account:** Click on the "Sign in with Google" button and enter valid credentials. If the login is sucessful and the account exists, the user will be redirected to the home page, otherwise, an error dialog will apear.
* **If the user doesn't has an account:** Click on the "Create a new account button" to navigate to the "Create Profile" screen.

In the Create Profile screen the user will be required to complete the following actions:
* Add at least two profile pictures. These can be obtained through the phone's photo library or the device camera. The necessary permissions are requested accordingly.
* Provide a user name.
* Provide a birth date. This will be used to calculate their age.
* Provide a gender (in order to simplify profile fetching only two options are available).
* Provide a preference: their own gender, the opposite gender or both.

A bio up to 500 characters is optional. The remaining amount of characters are shown as the user is typing.

Once the information has been filled in and the user clicks on the "Sign Up with Google button", if the user didn't exist before and the creation of the account was successful, the user will be redirected to the home page, otherwise an error dialog will appear.

## 🏠 Home
Here the user will be able to browse through profiles and swipe left or right on them in a Tinder-like fashion. Both swipe and button click to perform these actions are supported. If a user likes a user that has liked them before, a match will be created. Once a profile has been liked or disliked it will not be shown again to that user. From here the user can access to:
* The Edit Profile screen
* The Messages Screen

<p float="left">
  <img src="https://github.com/alejandro-piguave/TinderCloneCompose/blob/master/screenshots/home_screen.png" width="250" />
  <img src="https://github.com/alejandro-piguave/TinderCloneCompose/blob/master/screenshots/home_screen_dark.png" width="250" /> 
</p>

## 🧑‍💼 Edit Profile
In this screen the user can modify the same fields as in the "create profile" screen except for the name and birth date. Their design is almost identical.

<p float="left">
  <img src="https://github.com/alejandro-piguave/TinderCloneCompose/blob/master/screenshots/edit_profile_screen.gif" width="250" />
  <img src="https://github.com/alejandro-piguave/TinderCloneCompose/blob/master/screenshots/edit_profile_screen_dark.gif" width="250" /> 
</p>

## 💬 Chat
Here the user will be able to see their matches and access the corresponding Chat screen to send them messages.

<p float="left">
  <img src="https://github.com/alejandro-piguave/TinderCloneCompose/blob/master/screenshots/messages_screen.png" width="250" />
  <img src="https://github.com/alejandro-piguave/TinderCloneCompose/blob/master/screenshots/messages_screen_dark.png" width="250" /> 
    <img src="https://github.com/alejandro-piguave/TinderCloneCompose/blob/master/screenshots/chat_screen.png" width="250" />
  <img src="https://github.com/alejandro-piguave/TinderCloneCompose/blob/master/screenshots/chat_screen_dark.png" width="250" /> 
</p>


---

## Notes
- All the images used for testing purposes are taken from thispersondoesnotexist.com
- The file "google-services.json" inside the "app" directory that is required for the project to work is missing. You will need to connect it to your own Firebase project. However, you can test the application with mocked data selecting the "mock" build variant.
- Although the app is small, the domain and data layer is modularized by business capability (auth, profile, message, etc.) to showcase how a clean architecture would scale in a real-world app with independent domain and data boundaries.

