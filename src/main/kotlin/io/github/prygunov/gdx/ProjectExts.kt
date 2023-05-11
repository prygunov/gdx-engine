package io.github.prygunov.gdx

import org.gradle.api.Project
import org.gradle.api.provider.Property

@Suppress("UnstableApiUsage")
inline fun <reified T> Project.createProperty(): Property<T> {
    return this.objects.property(T::class.java)
}