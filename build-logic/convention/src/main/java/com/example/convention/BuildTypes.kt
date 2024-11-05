package com.example.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType
) {
    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }

        val mapkitApiKey =
            gradleLocalProperties(rootDir, rootProject.providers).getProperty("MAPKIT_API_KEY")

        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(mapkitApiKey)
                        }
                        release {
                            configureReleaseBuildType(commonExtension, mapkitApiKey)
                        }
                    }
                }
            }

            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(mapkitApiKey)
                        }
                        release {
                            configureReleaseBuildType(commonExtension, mapkitApiKey)
                        }
                    }
                }
            }
        }
    }
}

private fun BuildType.configureDebugBuildType(mapkitApiKey: String) {
    buildConfigField("String", "MAPKIT_API_KEY", "\"$mapkitApiKey\"")
    buildConfigField("String", "BASE_URL", "\"https://belarusbank.by/api/\"")
}

private fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    mapkitApiKey: String
) {
    buildConfigField("String", "MAPKIT_API_KEY", "\"$mapkitApiKey\"")
    buildConfigField("String", "BASE_URL", "\"https://belarusbank.by/api/\"")

    isMinifyEnabled = true
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}
