plugins {
    alias(libs.plugins.bbank.android.library.ui)
}

android {
    namespace = "com.example.core.presentation.ui"
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}