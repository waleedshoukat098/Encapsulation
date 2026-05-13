plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.techinnovation.learning"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.techinnovation.learning"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = "1.5.14" }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    implementation(project(":core:navigation"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":feature:home:api"))
    implementation(project(":feature:logmeal:api"))
    implementation(project(":feature:scan:api"))
    implementation(project(":feature:trends:api"))
    implementation(project(":feature:profile:api"))
    implementation(project(":feature:onboarding:api"))

    implementation(project(":feature:home:impl"))
    implementation(project(":feature:logmeal:impl"))
    implementation(project(":feature:scan:impl"))
    implementation(project(":feature:trends:impl"))
    implementation(project(":feature:profile:impl"))
    implementation(project(":feature:onboarding:impl"))

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.compose.ui:ui:1.7.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.7.2")
    implementation("androidx.compose.material3:material3:1.3.0")
    implementation("androidx.navigation:navigation-compose:2.8.0")
}
