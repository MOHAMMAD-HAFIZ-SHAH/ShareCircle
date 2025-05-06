plugins {
    alias(libs.plugins.android.application) // Ensure this alias is defined in libs.versions.toml
}

android {
    android {
        namespace = "com.example.rentapp"
        compileSdk = 35

        defaultConfig {
            applicationId = "com.example.rentapp"
            minSdk = 24
            targetSdk = 35  // <-- this should match or be close to compileSdk
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
    }

    dependencies {

        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.activity)
        implementation(libs.constraintlayout)
        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)
        implementation(libs.gson) // Ensure Gson is correctly referenced from the version catalog
        implementation("com.github.bumptech.glide:glide:4.16.0")
    }
}
