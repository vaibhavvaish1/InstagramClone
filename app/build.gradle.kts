import org.gradle.kotlin.dsl.implementation

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  id("com.google.devtools.ksp")
  id("com.google.dagger.hilt.android")
  alias(libs.plugins.google.gms.google.services)
}

android {
  namespace = "com.infinitysolutions.instagramclone"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.infinitysolutions.instagramclone"
    minSdk = 24
    targetSdk = 35
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
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
  buildFeatures {
    compose = true
  }
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
  // Coil
  implementation(libs.coil.compose)

  // Coroutines
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.coroutines.android)

  // Declare the dependencies for the desired Firebase products without specifying versions
  // For example, declare the dependencies for Firebase Authentication and Cloud Firestore
  implementation(platform("com.google.firebase:firebase-bom:33.14.0"))

// Firebase dependencies — no version needed
  implementation("com.google.firebase:firebase-auth")
  implementation("com.google.firebase:firebase-firestore")
  implementation("com.google.firebase:firebase-storage")
  implementation("com.google.dagger:hilt-android:2.56.2")
  ksp("com.google.dagger:hilt-android-compiler:2.56.2")
  implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

}


