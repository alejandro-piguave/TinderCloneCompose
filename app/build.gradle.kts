plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.apiguave.tinderclonecompose"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.apiguave.tinderclonecompose"
        minSdk = 26
        targetSdk = 34
        versionCode =  1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        create("mock") {
            initWith(getByName("debug"))
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled =  true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.ui)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material)

    implementation(libs.play.services.auth)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.coil)
    implementation(libs.coil.compose)

    implementation(libs.datetime)
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(project(":core:ui"))
    implementation(project(":feature:onboarding:ui"))
    implementation(project(":feature:onboarding:domain"))

    implementation(project(":feature:home:ui"))
    implementation(project(":feature:chat:ui"))
    implementation(project(":feature:editprofile:ui"))

    implementation(project(":core:auth:data"))
    implementation(project(":core:match:data"))
    implementation(project(":core:message:data"))
    implementation(project(":core:picture:data"))
    implementation(project(":core:profile:data"))

    implementation(project(":core:auth:domain"))
    implementation(project(":core:match:domain"))
    implementation(project(":core:message:domain"))
    implementation(project(":core:picture:domain"))
    implementation(project(":core:profile:domain"))


}