plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.hi.adjust"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(project(":HiGameSDKBase"))
    // ajust
    implementation ("com.adjust.sdk:adjust-android:4.31.0")

    // google ad id service
    implementation ("com.google.android.gms:play-services-ads-identifier:18.0.1")
    // Google Play Referrer API
    implementation ("com.android.installreferrer:installreferrer:2.2")
}