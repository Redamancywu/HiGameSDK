
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.google.services) apply false

}
buildscript {
    repositories {
        google()
        mavenCentral()

        maven { url = uri("https://artifacts.applovin.com/android") }
    }
    dependencies {
        classpath("com.applovin.quality:AppLovinQualityServiceGradlePlugin:+")
    }
}

