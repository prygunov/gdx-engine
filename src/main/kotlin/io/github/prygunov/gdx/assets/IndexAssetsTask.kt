package io.github.prygunov.gdx.assets

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import io.github.prygunov.gdx.createProperty
import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

open class IndexAssetsTask : DefaultTask() {

    init {
        group = "GDXEngine"
        description = "Create static class referencing assets file name."
    }

    @InputFiles
    val assetsDirectory = project.createProperty<FileCollection>()

    @Input
    val namespace = project.createProperty<String>()

    @OutputFile
    val assetsClass = project.createProperty<File>()

    @TaskAction
    fun generate() {
        val classFile = FileSpec.builder(namespace.get(), assetsClass.get().nameWithoutExtension)
        val classBuilder = TypeSpec.objectBuilder(assetsClass.get().nameWithoutExtension)

        assetsDirectory.get().files.forEach {
            val base = if (it.isDirectory) it else it.parentFile
            appendDirectory(it, base, classBuilder)
        }

        classFile.addType(classBuilder.build())
        classFile.build().writeTo(assetsClass.get().parentFile)
    }

    private fun appendDirectory(current: File, base: File, builder: TypeSpec.Builder, prefix: String = "") {
        if (current.name.startsWith('.') || current.nameWithoutExtension.isBlank())
            return
        if (current.isDirectory) {
            val dirName = current.nameWithoutExtension
            val dirClass = TypeSpec.objectBuilder(dirName)
            current.listFiles()?.forEach {
                appendDirectory(it, base, dirClass, prefix + current.nameWithoutExtension + "_")
            }
            builder.addType(dirClass.build())
        } else {
            var propName = current.nameWithoutExtension
            val alreadyExists = builder.propertySpecs.any { propertySpec -> propertySpec.name == propName }
            if (alreadyExists)
                propName = current.name

            builder.addProperty(
                PropertySpec.builder(propName.replace(".", "_"), String::class)
                    .initializer("%S", current.relativeTo(base).path)
                    .addModifiers(listOf(KModifier.CONST))
                    .build()
            )
        }
    }
}
