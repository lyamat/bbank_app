plugins {
    alias(libs.plugins.bbank.android.library)
    alias(libs.plugins.bbank.android.hilt.worker)
}

android {
    namespace = "com.example.news.data"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:data"))

    implementation(libs.androidx.work)
}