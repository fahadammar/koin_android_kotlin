plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.koininjectionwork"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.koininjectionwork"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        buildFeatures{
            buildConfig = true
        }
        debug {
            buildConfigField("boolean", "DEBUG", "true")
        }
        release {
            isMinifyEnabled = false
            buildConfigField("boolean", "DEBUG", "false")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    // KOIN BOM
    implementation(project.dependencies.platform("io.insert-koin:koin-bom:4.0.4"))
    // KOIN CORE
    implementation(libs.koin.core)
    // KOIN ANDROID
    implementation(libs.koin.android)

    // RETROFIT
    implementation (libs.retrofit)
    //moshi
    implementation(libs.moshi.kotlin)
    implementation (libs.converter.moshi)

    //RoomDB
    // Room components
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)


    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}