plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // Required for Firebase
}

android {
    namespace = "com.example.sharecircle"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.sharecircle"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures {
        viewBinding = true // Recommended for easier UI access
    }
}

dependencies {
    // Android core dependencies
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Firebase Authentication
    implementation(libs.firebase.auth)

    // Google Sign-In support
    implementation(libs.play.services.auth)

    // Firebase UI Auth (optional but useful for easy auth flows)
    implementation(libs.firebase.ui.auth)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
