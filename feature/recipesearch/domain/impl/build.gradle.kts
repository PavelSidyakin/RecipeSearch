plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.recipebook.recipesearch.domain.impl"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        consumerProguardFiles("consumer-rules.pro")
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

afterEvaluate {
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(project(":shared:logging"))
    implementation(project(":feature:recipesearch:domain:api"))
    implementation(project(":feature:recipesearch:data:api"))

    implementation(libs.hilt)

    ksp(libs.hilt.compiler)

    testImplementation(libs.testing.junit.jupiter)
    testImplementation(libs.testing.junit.jupiter.params)
    testImplementation(libs.testing.mockK)
    testImplementation(libs.testing.coroutines)
    testRuntimeOnly(libs.testing.junit.platformlauncher)
}
