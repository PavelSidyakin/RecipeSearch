package com.recipebook.testutils

private object Dummy

fun readFileFromResources(fileName: String): String {
    val inputStream = Dummy.javaClass.classLoader?.getResourceAsStream(fileName)

    return inputStream?.bufferedReader().use { it?.readText() ?: "" }
}
