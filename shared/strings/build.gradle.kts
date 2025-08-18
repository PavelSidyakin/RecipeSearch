plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.example.recipesearch.strings"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
}
