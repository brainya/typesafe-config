package com.brainya.typesafe.core

import com.brainya.typesafe.core.formats.properties.PropertiesFileTransformer
import com.brainya.typesafe.core.io.toJavaTemplateFile
import com.brainya.typesafe.core.model.NamedJavaType
import com.brainya.typesafe.core.model.TemplateFile
import java.io.File


fun transformToJavaClass(
        inputFile: File,
        outputFolder: File,
        outputFilePackage: String,
        outPutFileName: String
        ): TemplateFile {

    val javaType = NamedJavaType(outputFilePackage, outPutFileName)
    return PropertiesFileTransformer
            .transform(inputFile.inputStream())
            .toJavaTemplateFile(outputFolder,javaType )

}