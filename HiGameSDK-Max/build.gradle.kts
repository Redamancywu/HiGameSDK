plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.hi.ad"
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
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.applovin:applovin-sdk:12.4.0")
    implementation("com.applovin.mediation:google-adapter:22.6.0.0")
    implementation("com.applovin.mediation:facebook-adapter:6.16.0.2")
    implementation("com.applovin.mediation:mintegral-adapter:16.6.11.0")
    implementation("com.applovin.mediation:bytedance-adapter:5.7.0.2.0")
    implementation("com.applovin.mediation:google-ad-manager-adapter:22.6.0.0")
    implementation("com.applovin.mediation:smaato-adapter:22.5.1.0")
    implementation("com.applovin.mediation:fyber-adapter:8.2.5.0")
}