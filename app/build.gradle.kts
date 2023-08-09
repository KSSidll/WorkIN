plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.daggerHiltAndroid)
    kotlin("kapt")
    alias(libs.plugins.devtoolsKSP)
}

android {
    namespace = "com.kssidll.workin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kssidll.workin"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // temporary
            signingConfig = signingConfigs.getByName("debug")
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.accompanist.system.ui.controller)
    implementation(libs.appcompat)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.savedstate.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(platform(libs.compose.bom))
    androidTestImplementation(platform(libs.compose.bom))
    implementation(libs.compose)
    implementation(libs.compose.graphics)
    debugImplementation(libs.compose.tooling)
    implementation(libs.compose.tooling.preview)
    debugImplementation(libs.compose.test.manifest)
    androidTestImplementation(libs.compose.test.junit4)
    implementation(libs.material3)
    implementation(libs.navigation.compose)

    implementation(libs.room)
    ksp(libs.room.compiler)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.reorderable)
}