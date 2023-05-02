# SnapTrash

An android app built using [Kotlin](https://developer.android.com/kotlin) that allows users to report trash using a map and camera access.
It has been built following [MVVM Architecture](https://developer.android.com/topic/libraries/architecture/viewmodel) in the presentation layer as well as Jetpack components(including Jetpack Compose).

## Demo

//Screenshots and video

##  App specifications

* Android device must have version 5 or later installed to run the app.
* Developped with Kotlin programming language
* Jetpack Compose was used as UI toolkit
* The app is available in English, Finnish, French, Hungarian, German and Italian

## Architecture

We chose to use the MVVM (Model-View-ViewModel) architecture in our app as it provides clear separation of concerns between the UI, the business logic, and the data layer. 
This architecture allows us to easily maintain and test our codebase, as well as make changes to the UI or the data layer without affecting the other components. 

## Dependencies

* [Firebase](https://firebase.google.com/products-build) - offers a variety of backend services, including real-time database, cloud storage, and authentication that we used in our application.
* [OSMDroid](https://github.com/osmdroid/osmdroid) - enables map-related features that we used for locating the user to get the trash coordinates.
* [Camera2](https://developer.android.com/training/camera2) - enables taking the snaps and upload them.
* [Jetpack Compose](https://developer.android.com/jetpack/compose) - Android’s modern toolkit for building native UI.

## Compilation

To compile the application the user needs access to a firebase instance with authentication, cloud functions and firestore enabled. 
screenshots
