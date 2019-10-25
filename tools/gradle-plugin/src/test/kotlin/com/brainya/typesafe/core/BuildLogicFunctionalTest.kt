package com.brainya.typesafe.core

import org.assertj.core.api.Assertions
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.slf4j.LoggerFactory
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.nio.file.Path
import java.nio.file.Paths

class BuildLogicFunctionalTest {

    @get:Rule
    val testProjectDir = TemporaryFolder()

    private lateinit var buildFile: File
    private val logger = LoggerFactory.getLogger(javaClass)

    @Before
    fun setup() {
        buildFile = testProjectDir.newFile("build.gradle")
    }

    @Test
    fun testGenerateEntitiesTask() {


        val apiFile : Path = Paths.get("src/test/resources/someFile.properties").toAbsolutePath()

        Assertions.assertThat(apiFile).exists()

        val buildFileContent = """
        |plugins {
        |    id 'com.brainya.typesafe.properties-codegen'
        |}
        |
        |propertiesGenerator {
        |    FrontendApp {
        |       outputFolder = file('build/generated/java')
        |       configs {
        |           DbConfig {
        |               propertiesFile = file('$apiFile')
        |               pakage = "com.brainya.test"
        |           }
        |           SupportInfo {
        |               propertiesFile = file('$apiFile')
        |               pakage = 'com.brainya.another.test'
        |           }
        |       }
        |    }
        |}
        """.trimMargin()

        writeFile(buildFile, buildFileContent)
        val result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments("generateProperties", "--stacktrace")
                .withPluginClasspath()
                .build()

        logger.warn(result.output)

        Assertions.assertThat(result.task(":generateProperties")!!.outcome).isEqualTo(TaskOutcome.SUCCESS)

        Assertions.assertThat(
                testProjectDir.root.walkBottomUp().first { it.name == "SupportInfo.java" }
        ).isNotNull()
    }

    private fun writeFile(destination: File?, content: String) {
        var output: BufferedWriter? = null
        try {
            output = BufferedWriter(FileWriter(destination!!))
            output.write(content)
        } finally {
            output?.close()
        }
    }
}
