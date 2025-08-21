plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.serialization)
    alias(libs.plugins.room)
}

android {
    namespace = "com.recipebook.datasource.local"
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
    room {
        schemaDirectory("$projectDir/build/schemas")
    }
}

afterEvaluate {
    tasks.withType<Test> {
        useJUnitPlatform {
            includeEngines("junit-vintage")
        }
    }
}

dependencies {
    implementation(libs.hilt)
    implementation(libs.serialization)
    implementation(libs.room)
    implementation(libs.room.ktx)

    ksp(libs.hilt.compiler)
    ksp(libs.room.compiler)

    testImplementation(project(":shared:testutils"))
    testImplementation(libs.testing.junit4)
    testImplementation(libs.testing.junit5.jupiter)
    testImplementation(libs.testing.junit5.jupiter.params)
    testImplementation(libs.testing.mockK)
    testImplementation(libs.testing.coroutines)
    testImplementation(libs.testing.robolectric)
    testImplementation(libs.testing.android.test.core)
    testImplementation(libs.testing.android.arch.test.core)
    testRuntimeOnly(libs.testing.junit5.vintage)
    testRuntimeOnly(libs.testing.junit.platformlauncher)
}