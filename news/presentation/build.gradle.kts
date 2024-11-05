plugins {
    alias(libs.plugins.bbank.android.feature.ui)
    alias(libs.plugins.bbank.android.hilt)
}

android {
    namespace = "com.example.news.presentation"
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.androidx.recyclerview)
    implementation(libs.glide)
}