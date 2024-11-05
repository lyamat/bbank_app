plugins {
    alias(libs.plugins.bbank.android.feature.ui)
    alias(libs.plugins.bbank.android.hilt)
}

android {
    namespace = "com.example.department.presentation"
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.yandex.maps.mobile)
    implementation(libs.androidx.recyclerview)
}