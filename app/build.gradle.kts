import java.util.Properties

plugins {

    id("com.android.application")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp) // <-- Quédate solo con esta línea para KSP
    alias(libs.plugins.hilt.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.allengineeringinone"
    compileSdk = 36

    //config api key

    buildFeatures {
        buildConfig = true
    }

    val properties = Properties()
    val propertiesFile = rootProject.file("local.properties")
    if (propertiesFile.exists()) {
        propertiesFile.inputStream().use { properties.load(it) }
    }

    val gmapsApiKey: String = properties.getProperty("gmaps_api_key") ?: ""


    defaultConfig {
        applicationId = "com.example.allengineeringinone"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // config api key
        buildConfigField("String", "GMAPS_API_KEY", "\"$gmapsApiKey\"")
        manifestPlaceholders["gmapsApiKey"] = gmapsApiKey
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
    // Compose navigation
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    val nav_version = "2.9.3"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    implementation("com.google.dagger:hilt-android:2.57.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // firebase
    implementation(platform("com.google.firebase:firebase-bom:34.2.0"))
    implementation("com.google.firebase:firebase-firestore")
    ksp("com.google.dagger:hilt-compiler:2.57.1")


    // defaultDependencies

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

    // API
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")

    // Google Maps
    implementation("com.google.maps.android:maps-compose:4.4.1")
    implementation("com.google.android.gms:play-services-location:21.3.0")

    //hilt
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
}