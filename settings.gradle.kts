pluginManagement {
    repositories {
        google()
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

rootProject.name = "Encapsulation"

include(":app")
include(":core:common")
include(":core:model")
include(":core:domain")
include(":core:database")
include(":core:designsystem")
include(":core:ui")
include(":core:navigation")
include(":core:ml")

include(":feature:home:api", ":feature:home:impl")
include(":feature:logmeal:api", ":feature:logmeal:impl")
include(":feature:scan:api", ":feature:scan:impl")
include(":feature:trends:api", ":feature:trends:impl")
include(":feature:profile:api", ":feature:profile:impl")
include(":feature:onboarding:api", ":feature:onboarding:impl")
