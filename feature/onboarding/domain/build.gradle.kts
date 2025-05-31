plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.koin.core)
    implementation(project(":core:profile:domain"))
    implementation(project(":core:auth:domain"))
    implementation(project(":core:picture:domain"))
}