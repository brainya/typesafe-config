package com.brainya.typesafe.core

import org.junit.Assert
import org.junit.Test
import java.io.File


public class TestClass {
    @Test
    fun gnerateFile(){
        val templateFile = transformToJavaClass(
                File("src/test/resources/someFile.properties"),
                outputFolder = File("build/gensrc"),
                outPutFileName = "ConfigClass",
                outputFilePackage = "com.brainya.config"

        )
        Assert.assertEquals(templateFile.content,
                """
                        |package com.brainya.config;
                        |
                        |public final class ConfigClass {
                        |   public static final String DB_PASSWORD = "password";
                        |   public static final String DB_USER = "acbeni";
                        |   public static final String DB_URL = "localhost";
                        |}""".trimMargin()
                )
    }

}