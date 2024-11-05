import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidDaggerHiltWorkerConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
            }

            dependencies {
                "implementation"(libs.findLibrary("dagger-hilt-android").get())
                "ksp"(libs.findLibrary("dagger-hilt-android-compiler").get())
                "implementation"(libs.findLibrary("androidx-hilt-work").get())
                "ksp"(libs.findLibrary("androidx-hilt-compiler").get())
            }
        }
    }
}
