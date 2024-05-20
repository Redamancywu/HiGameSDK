plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("D:\\HiGameSDK\\app\\HiGame.jks")
            storePassword = "240520"
            keyAlias = "HiGame"
            keyPassword = "240520"
        }
        create("release") {
            storeFile = file("D:\\HiGameSDK\\app\\HiGame.jks")
            storePassword = "240520"
            keyAlias = "HiGame"
            keyPassword = "240520"
        }
    }
    namespace = "com.hi.sdkdemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hi.sdkdemo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding{
        enable = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(project(":HiGameSDKBase"))
    implementation(project(":HiGameSDK-GooglePay"))
    implementation(project(":HiGameSDK-Adamob"))
    implementation(project(":HiGameSDK-Login"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}