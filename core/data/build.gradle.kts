plugins {
    alias(libs.plugins.bbank.android.library)
    alias(libs.plugins.bbank.android.hilt)
    alias(libs.plugins.bbank.jvm.ktor)
}

android {
    namespace = "com.example.core.data"
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.gson)
}