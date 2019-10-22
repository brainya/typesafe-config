package com.brainya.typesafe.core.io

import com.brainya.typesafe.core.model.Node
import com.brainya.typesafe.core.model.TemplateFile
import com.brainya.typesafe.core.utils.keepIndentation
import java.nio.file.Path
import java.nio.file.Paths



fun Node<String>.toKotlinTemplateFile(basePath: Path) = TemplateFile(
        path = Paths.get(basePath.toString(),this.data ),
        content = this.toKtObject()
)

private fun Node<String>.toKtObject(): String{

    if(this.children[0].children.isEmpty()){
        return """val ${this.data} = "${this.children[0].data}""""
    }
    else {
        return """
            |object ${this.data.capitalize()} {
            |   <${this.children.map { it.toKtObject() }.joinToString(separator = "\n")}>
            |}
        """.trimMargin().keepIndentation()
    }
}
