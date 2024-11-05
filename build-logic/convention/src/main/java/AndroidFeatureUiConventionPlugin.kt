import com.android.build.api.dsl.LibraryExtension
import com.example.convention.addUiLayerDependencies
import com.example.convention.configureAndroidViewBinding
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureUiConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("bbank.android.library")
            }

            dependencies {
                addUiLayerDependencies(target)
            }

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidViewBinding(extension)
        }
    }
}