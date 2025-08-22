package com.recipebook.testutils

import com.recipebook.utils.coroutineutils.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Stub with the specified [kotlinx.coroutines.CoroutineDispatcher] for all dispatchers.
 * @property dispatcher Optional [CoroutineDispatcher] to set
 */
class DispatcherProviderStub(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Unconfined,
) : DispatcherProvider {
    override fun io(): CoroutineDispatcher = dispatcher
    override fun main(): CoroutineDispatcher = dispatcher
}
