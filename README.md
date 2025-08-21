# RecipeSearch

A sample application. Demonstrates using the clean architecture concept.

## Build 
Add your Spoonacular API KEY to `gradle.properties` file in the [gradle home directory](https://docs.gradle.org/current/userguide/directory_layout.html#dir:gradle_user_home) or directly in the project (not recommended):
```
spoonacularApiKey=<YOUR API KEY>
```
Then build the project in Android Studio and run. 
Or build directly with Gradle:
```
gradlew assembleDebug
```
Then install the APK file  `\app\build\outputs\apk\debug\app-debug.apk` on your device or emulator. 

## Description

Main used technologies/libraries: Kotlin Coroutines, Hilt, Compose, Paging 3, Coil, NavComponent, MVVM, Room DB, Retrofit, Junit5, Mockk.

Main architecture concept: Clean Architecture (MVVM for UI).

Modules structure (excluding app and utils):

<img height="1000" src="https://github.com/PavelSidyakin/RecipeSearch/blob/main/RecipeBook_module_diagram.png">
