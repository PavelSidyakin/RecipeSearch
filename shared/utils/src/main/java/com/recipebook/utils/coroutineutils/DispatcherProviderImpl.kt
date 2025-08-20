package com.recipebook.utils.coroutineutils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Provides default [CoroutineDispatcher]s.
 */
internal class DispatcherProviderImpl @Inject constructor(
) : DispatcherProvider {

    override fun main(): CoroutineDispatcher = Dispatchers.Main
    override fun mainImmediate(): CoroutineDispatcher = Dispatchers.Main.immediate
    override fun io(): CoroutineDispatcher = Dispatchers.IO

}
