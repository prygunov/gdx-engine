package io.github.prygunov.gdx.assets

import io.github.prygunov.gdx.createProperty
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import java.io.File

open class AssetsExtension(project: Project) {

    /**
     * Which directory should be scan so all files will be referenced in the Assets object.
     */
    val assetsDirectory = project.createProperty<FileCollection>()
        .value(project.files("assets"))


    val namespace = project.createProperty<String>()
        .value("com.example.app")

    /**
     * Which class (aka R object) will reference all assets name.
     */
    val assetsClass = project.createProperty<File>()
        .value(project.buildDir.resolve("generated/source/kapt/main/R.kt"))

}