package com.brainya.typesafe.config

private const val kotlinVersion = "1.3.50"
private const val jacksonVersion = "2.10.0"
private const val testRunnerVersion = "3.3.2"



object Dependencies {
    object Kotlin {
        val kotlin_std = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
    }
    object Jackosn {
        val data_bind = "com.fasterxml.jackson.core:jackson-databind:$jacksonVersion"
    }
    object test{
        val testRunner =  "io.kotlintest:kotlintest-runner-junit5:$testRunnerVersion"
        val junit = "junit:junit:4.13-beta-3"
        val assertJ = "org.assertj:assertj-core:3.13.2"

    }
}
