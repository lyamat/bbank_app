plugins {
    alias(libs.plugins.bbank.android.application.xml)
    alias(libs.plugins.bbank.android.hilt.worker)
}

android {
    namespace = "com.example.bbank"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:database"))
    implementation(project(":core:data"))
    implementation(project(":core:presentation:ui"))
    implementation(project(":news:data"))
    implementation(project(":news:network"))
    implementation(project(":news:presentation"))
    implementation(project(":department:network"))
    implementation(project(":department:presentation"))
    implementation(project(":converter:presentation"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.preference.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.material)
    implementation(libs.yandex.maps.mobile)
}