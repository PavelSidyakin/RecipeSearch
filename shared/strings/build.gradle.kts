plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.recipebook.strings"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
}
