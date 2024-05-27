buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
    }
}
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
}