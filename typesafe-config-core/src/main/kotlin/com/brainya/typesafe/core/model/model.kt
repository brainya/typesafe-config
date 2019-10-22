package com.brainya.typesafe.core.model

import java.nio.file.Path


data class Node<T>(val data:T,val children: List<Node<T>>)
data class TemplateFile(val path: Path, val content: String)