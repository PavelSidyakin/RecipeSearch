package com.recipebook.utils.di

import com.recipebook.utils.coroutineutils.DispatcherProvider
import com.recipebook.utils.coroutineutils.DispatcherProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface UtilsModule {

    @Binds
    fun bindDispatcherProvider(impl: DispatcherProviderImpl): DispatcherProvider
}
