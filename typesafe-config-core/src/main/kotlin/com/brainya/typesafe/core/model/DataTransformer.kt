package com.brainya.typesafe.core.model

import com.brainya.typesafe.core.model.Node
import java.io.InputStream

interface DataTransformer {

    fun transform(inputStream: InputStream): Node<String>

}