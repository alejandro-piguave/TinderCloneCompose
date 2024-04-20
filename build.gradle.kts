buildscript {
    val agp_version by extra("8.0.0")
}
plugins {
    id ("com.android.application") version "8.0.0" apply false
    id ("com.android.library") version "7.2.2" apply false
    id ("org.jetbrains.kotlin.android") version "1.7.10" apply false
    id ("com.google.gms.google-services") version "4.4.1" apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
}
