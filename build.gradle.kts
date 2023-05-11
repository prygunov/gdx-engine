plugins {
    kotlin("jvm") version "1.8.20"
    application
    id ("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "1.1.0"
}

group = "io.github.prygunov"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    //implementation(project(":commons-gradle-plugin"))
    implementation("com.squareup:kotlinpoet:1.13.2")
    implementation("com.squareup:javapoet:1.10.0")
    compileOnly(gradleApi())
    implementation(gradleApi())
    implementation(kotlin("stdlib"))
    testImplementation(gradleTestKit())
}

tasks.test {
    useJUnitPlatform()
}


pluginBundle {
    description = "A plugin that helps you test your plugin against a variety of Gradle versions"
    website = "https://gitlab.com/ysb33rOrg/gradleTest"
    vcsUrl = "https://gitlab.com/ysb33rOrg/gradleTest.git"

    tags = arrayListOf("testing", "integrationTesting", "compatibility")
}


gradlePlugin {

    plugins {
        create("gdx") {
            id = "gdx"
            implementationClass = "io.github.prygunov.gdx.GDXPlugin"

            displayName = "Plugin for libgdx projects"
            description = "Some desc"
            //tags.set(listOf("game-engine", "libgdx", "compatibility"))
        }
    }


}