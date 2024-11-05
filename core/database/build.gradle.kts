plugins {
    alias(libs.plugins.bbank.android.library)
    alias(libs.plugins.bbank.android.room)
    alias(libs.plugins.bbank.android.hilt)
}

android {
    namespace = "com.example.core.database"
}

dependencies {
    implementation(project(":core:domain"))
}