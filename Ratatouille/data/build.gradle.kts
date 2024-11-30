plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ru.mirea.andreevapk.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Domain layer dependency
    implementation(project(":domain"))

    // Core AndroidX libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Firebase Auth
    implementation(libs.firebase.auth.ktx)

    // Room dependencies
    implementation(libs.androidx.room.runtime)  // Room runtime
    implementation(libs.androidx.room.ktx)      // Kotlin extensions for Room
    ksp(libs.androidx.room.compiler)            // Use KSP for annotation processing

    // LiveData
    implementation(libs.androidx.lifecycle.livedata)      // Kotlin extensions for Room

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}