plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.techinnovation.learning.feature.trends.impl"
    compileSdk = 34
    defaultConfig { minSdk = 26 }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures { compose = true }
}

dependencies {
    implementation("androidx.compose.material3:material3:1.3.0")
    implementation("androidx.navigation:navigation-compose:2.8.0")
    implementation("androidx.compose.ui:ui:1.7.2")
    implementation("androidx.compose.runtime:runtime:1.7.2")
    implementation(project(":feature:trends:api"))
    implementation(project(":core:navigation"))
    implementation(project(":core:domain"))
}
