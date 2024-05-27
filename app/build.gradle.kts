plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.gms.google-services")
}

object Libs {
    const val androidx_core_version = "1.7.0"
    const val androidx_appcompat_version = "1.4.0"
    const val material_version = "1.5.0"
    const val androidx_activity_version = "1.3.1"
    const val constraintlayout_version = "2.1.2"
    const val junit_version = "4.13.2"
    const val androidx_junit_version = "1.1.3"
    const val espresso_core_version = "3.4.0"
}

android {
    namespace = "co.edu.eam.fakemaps"

    compileSdk = 34

    defaultConfig {
        applicationId = "co.edu.eam.fakemaps"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:${Libs.androidx_core_version}")
    implementation("androidx.appcompat:appcompat:${Libs.androidx_appcompat_version}")
    implementation("com.google.android.material:material:${Libs.material_version}")
    implementation("androidx.activity:activity-ktx:${Libs.androidx_activity_version}")
    implementation("androidx.constraintlayout:constraintlayout:${Libs.constraintlayout_version}")
    implementation(libs.play.services.cast.framework)
    implementation(libs.firebase.perf.ktx)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.activity)
    implementation("com.google.firebase:firebase-firestore-ktx:24.3.0")
    testImplementation("junit:junit:${Libs.junit_version}")
    androidTestImplementation("androidx.test.ext:junit:${Libs.androidx_junit_version}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Libs.espresso_core_version}")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.android.gms:play-services-location:21.0.0")
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-analytics")
}
