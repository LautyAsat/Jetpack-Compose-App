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
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
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
    implementation("com.google.firebase:firebase-database")
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

    //CameraX
//    implementation(libs.androidx.camera.core)
//    implementation(libs.androidx.camera.compose)
//    implementation(libs.androidx.camera.lifecycle)
//    implementation(libs.androidx.camera.camera2)
    implementation(libs.accompanist.permissions)

    val camerax_version = "1.5.0"

    // The following line is optional, as the core library is included indirectly by camera-camera2
    implementation("androidx.camera:camera-core:${camerax_version}")
    implementation("androidx.camera:camera-camera2:${camerax_version}")
    // If you want to additionally use the CameraX Lifecycle library
    implementation ("androidx.camera:camera-lifecycle:${camerax_version}")
    // If you want to additionally use the CameraX VideoCapture library
    implementation ("androidx.camera:camera-video:${camerax_version}")
    // If you want to additionally use the CameraX View class
    implementation ("androidx.camera:camera-view:${camerax_version}")
    // If you want to additionally add CameraX ML Kit Vision Integration
    implementation ("androidx.camera:camera-mlkit-vision:${camerax_version}")
    // If you want to additionally use the CameraX Extensions library
    implementation ("androidx.camera:camera-extensions:${camerax_version}")

    implementation("com.google.guava:guava:31.0.1-android")
    implementation("com.google.guava:listenablefuture:1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-guava:1.6.0")


    // Icons
    implementation("androidx.compose.material:material-icons-extended:1.6.7")

    // ARCore
//    implementation("androidx.xr.arcore:arcore:1.0.0-alpha05")
//    implementation("androidx.xr.arcore:arcore-guava:1.0.0-alpha05")
//    implementation("androidx.xr.arcore:arcore-rxjava3:1.0.0-alpha05")

    // ARCore (si no lo tenés ya)
    implementation("com.google.ar:core:1.50.0")
    // Sceneform y ArFragment (el kit para ensamblar la UI de AR)
    //implementation(libs.sceneform.ux)
    implementation("io.github.sceneview:arsceneview:2.3.0")
    //implementation("io.github.sceneview:sceneview:2.3.0")

    //Appcompact
    implementation(libs.androidx.appcompat)
}