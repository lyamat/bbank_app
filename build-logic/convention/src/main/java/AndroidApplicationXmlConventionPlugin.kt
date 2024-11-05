import com.android.build.api.dsl.ApplicationExtension
import com.example.convention.configureAndroidViewBinding
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationXmlConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("bbank.android.application")
            }

            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidViewBinding(extension)
        }
    }
}
