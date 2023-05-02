# SnapTrash

An android app built using [Kotlin](https://developer.android.com/kotlin) that allows users to report trash using a map and camera access.
It has been built following [MVVM Architecture](https://developer.android.com/topic/libraries/architecture/viewmodel) in the presentation layer as well as Jetpack components(including Jetpack Compose).

## Demo
<img src="https://user-images.githubusercontent.com/66414968/235723111-613acc2b-6945-4dd2-a8e6-f44877256aad.jpg" width=150 height=300> | <img src="https://user-images.githubusercontent.com/66414968/235723168-a5a54eb8-1b37-40ec-bb8f-50c3cb0b3a52.jpg" width=150 height=300>
<img src="https://user-images.githubusercontent.com/66414968/235723224-ac3900eb-1788-4a9c-86c3-a49c9c154b36.jpg" width=150 height=300>
<img src="https://user-images.githubusercontent.com/66414968/235723249-c69c2ccf-ad79-44ff-8cf6-d5763bcea1a3.jpg" width=150 height=300>
<img src="https://user-images.githubusercontent.com/66414968/235728115-9d697b8c-74cf-4505-96de-02cdd856cac6.jpg" width=150 height=300>
<img src="https://user-images.githubusercontent.com/66414968/235728130-fea5cb6e-a863-467a-8d7e-d231140c36d3.jpg" width=150 height=300>


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
