import com.brainya.typesafe.config.Dependencies

plugins{
    id("java-gradle-plugin")
}

dependencies {
    implementation(project(":typesafe-config-core"))
    implementation(gradleApi())
    testImplementation(gradleTestKit())
    testImplementation(Dependencies.test.assertJ)
    testImplementation(Dependencies.test.junit)
}


gradlePlugin {
    plugins {
        create("CodegenPlugin") {
            id = "com.brainya.typesafe.properties-codegen"
            implementationClass = "com.brainya.typesafe.gradle.CodegenPlugin"
        }
    }
}


tasks.register("createClasspathManifest") {
    val outputDir = file("$buildDir/$name")

    inputs.files(sourceSets.main.get().runtimeClasspath)
            .withPropertyName("runtimeClasspath")
            .withNormalizer(ClasspathNormalizer::class)
    outputs.dir(outputDir)
            .withPropertyName("outputDir")

    doLast {
        outputDir.mkdirs()
        file("$outputDir/plugin-classpath.txt").writeText(sourceSets.main.get().runtimeClasspath.joinToString("\n"))
    }
}

// Add the classpath file to the test runtime classpath
dependencies {
    testRuntimeOnly(files(tasks["createClasspathManifest"]))
}
