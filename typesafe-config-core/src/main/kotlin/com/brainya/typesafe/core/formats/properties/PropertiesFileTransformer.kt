package com.brainya.typesafe.core.formats.properties

import com.brainya.typesafe.core.model.DataTransformer
import com.brainya.typesafe.core.model.Node
import java.io.InputStream
import java.util.*

object PropertiesFileTransformer : DataTransformer {

    override fun transform(inputStream: InputStream): Node<String> {
        val properties: Properties = Properties();
        properties.load(inputStream)
        return Node("", properties.toDataPath().toTree(0))
    }
}


fun List<DataPath<String>>.toTree(depth: Int): List<Node<String>> {
    return this
            .groupBy {
                it.path[depth]
            }
            .map { entry ->
                val children: List<Node<String>> =
                        entry.value.filter { it.path.size > depth + 1 }.toTree(depth + 1)
                                .plus(
                                        entry.value.filter { it.path.size == depth + 1 }
                                                .map {
                                                    Node(data = it.data, children = listOf())
                                                }
                                )
                Node(data = entry.key, children = children)
            }
}

fun Properties.toDataPath(): List<DataPath<String>> = this.map {
    DataPath((it.key as String).split("."), it.value as String)
}

data class DataPath<T>(val path: List<String>, val data: T)
