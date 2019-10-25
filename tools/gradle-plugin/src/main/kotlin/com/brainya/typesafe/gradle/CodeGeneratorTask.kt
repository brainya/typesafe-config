package com.brainya.typesafe.gradle


import com.brainya.typesafe.core.transformToJavaClass
import com.brainya.typesafe.core.utils.writeFileContent
import org.gradle.api.DefaultTask
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.tasks.TaskAction
import java.io.File

open class CodeGeneratorTask : DefaultTask() {


    @TaskAction
    internal fun generateClasses() {

        val targets = project.extensions.getByName("propertiesGenerator") as NamedDomainObjectContainer<PropertiesGenerator>


        targets.forEach { generator ->
            run {
                val outputFolder = generator.outputFolder
                if (outputFolder == null ) {
                    logger.error("outputFolder should be specified")
                    return
                }
                if(!outputFolder.exists() || !outputFolder.isFile){
                    outputFolder.mkdirs()
                }
                generator.configs?.all { config: Config -> generateFiles(outputFolder, config) }
            }
        }
    }

    fun generateFiles(outputFolder: File, config: Config): Unit {

        val inputFile = config.propertiesFile ?: throw Exception("The input properties file should be defined")

        transformToJavaClass(
                outputFolder = outputFolder,
                outputFilePackage = config.pakage?:"com.brainya",
                outPutFileName = config.name,
                inputFile = inputFile
        ).writeFileContent()

    }

}
