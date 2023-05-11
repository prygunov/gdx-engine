package io.github.prygunov.gdx


import io.github.prygunov.gdx.assets.AssetsExtension
import io.github.prygunov.gdx.assets.IndexAssetsTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet

class GDXPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val exts = project.extensions.create("assets", AssetsExtension::class.java, project)
        println("Applied")
        project.tasks.register("indexAssets", IndexAssetsTask::class.java) { task ->
            task.assetsDirectory.set(exts.assetsDirectory)
            task.namespace.set(exts.namespace)
            task.assetsClass.set(exts.assetsClass)

//            task.dependsOn(project.tasks.getByName("compileKotlin"))
        }

        project.plugins.withType(JavaPlugin::class.java) {
            val javaConvention = project.convention.getPlugin(JavaPluginConvention::class.java)
            val main = javaConvention.sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME)
            main.java.srcDir(project.buildDir.resolve("generated"))
        }
        println("FINISH")
    }
}