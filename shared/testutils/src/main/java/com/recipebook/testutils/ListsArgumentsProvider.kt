package com.recipebook.testutils

import androidx.annotation.VisibleForTesting
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.support.ParameterDeclarations
import java.util.stream.Stream

@VisibleForTesting
fun valueListsArgumentsProvider(
    vararg lists: List<Any>,
): ArgumentsProvider {
    val arguments: MutableList<Arguments> = mutableListOf()

    if (lists.isEmpty()) throw IllegalArgumentException("No arguments provided")
    val emptyListIndex = lists.indexOfFirst { it.isEmpty() }
    if (emptyListIndex >= 0) throw IllegalArgumentException("Empty list at index: $emptyListIndex")

    when {
        lists.size == 1 -> {
            lists[0].forEach { arguments.add(Arguments.arguments(it)) }
        }

        lists.size > 1 -> {
            cartesianProduct(
                a = lists[0].toSet(),
                b = lists[1].toSet(),
                *lists.takeLast(lists.size - 2).map { it.toSet() }.toTypedArray(),
            ).forEach { argumentsList: List<Any> ->
                arguments.add(Arguments.arguments(*argumentsList.toTypedArray()))
            }
        }
    }

    return object : ArgumentsProvider {
        override fun provideArguments(
            parameters: ParameterDeclarations,
            context: ExtensionContext,
        ): Stream<out Arguments> {
            return arguments.stream()
        }
    }
}

private fun <T> cartesianProduct(a: Set<T>, b: Set<T>, vararg sets: Set<T>): Set<List<T>> =
    (listOf(a, b).plus(sets))
        .fold(listOf(listOf<T>())) { acc, set ->
            acc.flatMap { list -> set.map { element -> list + element } }
        }
        .toSet()
