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
    }
}

rootProject.name = "ru.mirea.Andreeva.Lesson4"
include(":app")
include(":player")
include(":thread")
include(":thread_pair_calculation")
include(":data_thread")
include(":looper")
include(":cryptoloader")
include(":serviceapp")
include(":workmanager")
