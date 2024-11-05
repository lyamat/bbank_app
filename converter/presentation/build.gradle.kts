plugins {
    alias(libs.plugins.bbank.android.feature.ui)
    alias(libs.plugins.bbank.android.hilt)
}

android {
    namespace = "com.example.converter.presentation"
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.recyclerview)
}