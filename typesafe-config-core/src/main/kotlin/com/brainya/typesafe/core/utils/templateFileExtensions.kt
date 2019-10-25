package com.brainya.typesafe.core.utils

import com.brainya.typesafe.core.model.TemplateFile
import java.io.File

fun TemplateFile.writeFileContent() {
    val file = this.path.toFile()
    file.parentFile.mkdirs()
    file.createNewFile()
    file.writeText(this.content)
}