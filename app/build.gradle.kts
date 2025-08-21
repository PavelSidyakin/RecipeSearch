plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.recipebook"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.recipebook"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(project(":shared:strings"))
    implementation(project(":shared:logging"))
    implementation(project(":feature:mainnavigation:mainactivity"))
    implementation(project(":datasource:local"))
    implementation(project(":datasource:remote"))
    implementation(project(":feature:recipesearch:domain:api"))
    implementation(project(":feature:recipesearch:data:api"))
    implementation(project(":feature:recipesearch:domain:impl"))
    implementation(project(":feature:recipesearch:data:impl"))
    implementation(project(":feature:recipedetails:domain:api"))
    implementation(project(":feature:recipedetails:data:api"))
    implementation(project(":feature:recipedetails:domain:impl"))
    implementation(project(":feature:recipedetails:data:impl"))
    implementation(project(":feature:viewedrecipes:presentation"))
    implementation(project(":feature:viewedrecipes:domain:api"))
    implementation(project(":feature:viewedrecipes:domain:impl"))
    implementation(project(":feature:viewedrecipes:data:api"))
    implementation(project(":feature:viewedrecipes:data:impl"))
    implementation(project(":feature:viewedrecipes:models"))

    implementation(libs.hilt)

    ksp(libs.hilt.compiler)
}
