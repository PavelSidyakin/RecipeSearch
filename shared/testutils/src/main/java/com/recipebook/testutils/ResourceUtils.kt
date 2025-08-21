package com.recipebook.testutils

private object Dummy

/**
 * Returns a java resource file content as a string.
 *
 * @param fileName The name of the file.
 */
fun readFileFromResources(fileName: String): String {
    val inputStream = Dummy.javaClass.classLoader?.getResourceAsStream(fileName)

    return inputStream?.bufferedReader().use { it?.readText() ?: "" }
}
