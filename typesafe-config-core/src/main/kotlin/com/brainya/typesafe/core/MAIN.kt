package com.brainya.typesafe.core

import com.brainya.typesafe.core.formats.properties.PropertiesFileTransformer
import com.brainya.typesafe.core.io.toJavaTemplateFile
import com.brainya.typesafe.core.io.toKotlinTemplateFile
import java.nio.file.Paths

fun main() {

    val propertiesFile = """|
        |com.brainya.typesafe.name=typesafe-plugin
        |com.brainya.typesafe.version=0.0.1
        |com.brainya.typesafe=acbeni
    """.trimMargin()
    val targetStream = propertiesFile.byteInputStream()
    val res = PropertiesFileTransformer.transform("Properties", targetStream).toJavaTemplateFile(Paths.get(""))
    println(res.content)
}