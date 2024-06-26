pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://artifacts.applovin.com/android") }
    }
}


rootProject.name = "HiGameSDK"
include(":app")
include(":HiGameSDKBase")
include(":HiGameSDK-GooglePay")
include(":HiGameSDK-Login")
include(":HiGameSDK-Adamob")
include(":HiGameSDK-Adjust")
include(":HiGameSDK-Max")
