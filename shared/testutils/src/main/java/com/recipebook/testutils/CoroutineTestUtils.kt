package com.recipebook.testutils

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@VisibleForTesting
suspend fun <T> Flow<T>.collectEmits(
    action: suspend () -> Unit,
): List<T> {
    return coroutineScope {
        val emits = mutableListOf<T>()
        launch(Job() + Dispatchers.Unconfined) {
            collect { emits.add(it) }
        }
        action()
        emits
    }
}
