package com.brainya.typesafe.core.io

import com.brainya.typesafe.core.model.Node
import com.brainya.typesafe.core.model.TemplateFile
import com.brainya.typesafe.core.utils.keepIndentation
import java.nio.file.Path
import java.nio.file.Paths


fun Node<String>.toJavaTemplateFile(basePath: Path) = TemplateFile(
        path = Paths.get(basePath.toString(), this.data),
        content = this.toJavaFile()
)

private fun Node<String>.toJavaFile(): String {

    return """|
        |public final class ${this.data} {
        |   <${this.children.flatMap { it.toJavaProperty(listOf()) }.joinToString(separator = "\n")}>
        |}
        """
            .trimMargin()
            .keepIndentation()
}

fun Node<String>.toJavaProperty(parentPath: List<String>): List<String> {
    if (this.children.isEmpty()) {
        val propertyName = parentPath.map { it.toUpperCase() }.joinToString(separator = "_")
        return listOf("public static final String $propertyName = \"${this.data}\";")
    } else {
        return this.children.flatMap {
            it.toJavaProperty(parentPath + this.data)
        }
    }
}


