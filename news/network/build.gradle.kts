plugins {
    alias(libs.plugins.bbank.android.library)
    alias(libs.plugins.bbank.jvm.ktor)
    alias(libs.plugins.bbank.android.hilt)
}

android {
    namespace = "com.example.news.network"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:data"))

    implementation(libs.org.mongodb.bson)
}