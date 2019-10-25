package com.brainya.typesafe.gradle

import groovy.lang.Closure
import org.gradle.api.NamedDomainObjectContainer
import java.io.File


open class PropertiesGenerator constructor(val name: String) {

    var outputFolder: File? = null
    var configs: NamedDomainObjectContainer<Config>? = null
    fun configs(closure: Closure<Config>) {
        configs?.configure(closure)
    }
}

open class Config constructor(val name: String) {

    var propertiesFile: File? = null
    var pakage: String? = null
}

