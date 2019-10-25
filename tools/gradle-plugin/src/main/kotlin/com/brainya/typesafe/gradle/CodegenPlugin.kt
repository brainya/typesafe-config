package com.brainya.typesafe.gradle

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project

open class CodegenPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        setupProject(project)
        createTasks(project)
    }

    fun setupProject(project: Project){
        val generators : NamedDomainObjectContainer<PropertiesGenerator>  =  project.container(PropertiesGenerator::class.java)
        generators.all { generator: PropertiesGenerator?->   generator?.configs = project.container(Config::class.java)}
        project.extensions.add("propertiesGenerator", generators)
    }

    fun createTasks(project: Project){
        project.tasks.create("generateProperties", CodeGeneratorTask::class.java)
    }
}
