plugins {
    id("com.android.application") version "8.5.0" apply false
    id("com.google.dagger.hilt.android") version "2.51" apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
}