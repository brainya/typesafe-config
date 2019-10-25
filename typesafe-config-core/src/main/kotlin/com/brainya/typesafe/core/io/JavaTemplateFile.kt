package com.brainya.typesafe.core.io

import com.brainya.typesafe.core.model.NamedJavaType
import com.brainya.typesafe.core.model.Node
import com.brainya.typesafe.core.model.TemplateFile
import com.brainya.typesafe.core.utils.keepIndentation
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths


fun Node<String>.toJavaTemplateFile( outputFolder:File,javaType: NamedJavaType):TemplateFile {

    val filePath = Paths.get(outputFolder.toString(), javaType.pakage.replace(".","/"),"${javaType.className}.java")

    return TemplateFile(
            path = filePath,
            content = this.toJavaFile(javaType)
    )
}

private fun Node<String>.toJavaFile(javaType: NamedJavaType): String {

    return """
        |package ${javaType.pakage};
        |
        |public final class ${javaType.className} {
        |   <${this.children.flatMap { it.toJavaProperty(listOf()) }.joinToString(separator = "\n")}>
        |}
        """
            .trimMargin()
            .keepIndentation()
}

fun Node<String>.toJavaProperty(parentPath: List<String>): List<String> {
    return if (this.children.isEmpty()) {
        val propertyName = parentPath.map { it.toUpperCase() }.joinToString(separator = "_")
        listOf("public static final String $propertyName = \"${this.data}\";")
    } else {
        this.children.flatMap {
            it.toJavaProperty(parentPath + this.data)
        }
    }
}


