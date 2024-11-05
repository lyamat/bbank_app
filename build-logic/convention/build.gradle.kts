plugins {
    `kotlin-dsl`
}

group = "com.example.bbank.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "bbank.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationXml") {
            id = "bbank.android.application.xml"
            implementationClass = "AndroidApplicationXmlConventionPlugin"
        }
        register("androidDaggerHilt") {
            id = "bbank.android.hilt"
            implementationClass = "AndroidDaggerHiltConventionPlugin"
        }
        register("androidDaggerHiltWorker") {
            id = "bbank.android.hilt.worker"
            implementationClass = "AndroidDaggerHiltWorkerConventionPlugin"
        }
        register("androidFeatureUi") {
            id = "bbank.android.feature.ui"
            implementationClass = "AndroidFeatureUiConventionPlugin"
        }
        register("androidLibraryUi") {
            id = "bbank.android.library.ui"
            implementationClass = "AndroidLibraryUiConventionPlugin"
        }
        register("androidLibrary") {
            id = "bbank.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidRoom") {
            id = "bbank.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("jvmLibrary") {
            id = "bbank.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("jvmKtor") {
            id = "bbank.jvm.ktor"
            implementationClass = "JvmKtorConventionPlugin"
        }
    }
}