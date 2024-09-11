pluginManagement {
    includeBuild("build-logic")
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
        flatDir {
            dirs ("libs")
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))
rootProject.name = "PLUV"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core")
include(":core:data")
include(":core:model")
include(":core:network")
include(":core:datastore")
include(":core:designsystem")
include(":core:ui")
include(":feature")
include(":feature:migrate")
include(":feature:common")
include(":feature:feed")
include(":feature:home")
include(":feature:login")
include(":feature:history")
include(":feature:mypage")
include(":feature:onboarding")
//include(":feature:splash")
