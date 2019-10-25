import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
}

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.3.50"))
    }
}

allprojects {

    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    apply {
        plugin("org.jetbrains.kotlin.jvm")
    }

    dependencies{
        implementation(kotlin("stdlib-jdk8"))
    }

    val compileKotlin: KotlinCompile by tasks
    compileKotlin.kotlinOptions {
        jvmTarget = "1.8"
    }
    val compileTestKotlin: KotlinCompile by tasks
    compileTestKotlin.kotlinOptions {
        jvmTarget = "1.8"
    }
}