package com.recipebook.viewedrecipes.data.impl.di

import com.recipebook.viewedrecipes.data.api.ViewedRecipesRepository
import com.recipebook.viewedrecipes.data.impl.ViewedRecipesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface ViewedRecipesDataModule {

    @Binds
    fun bindViewedRecipesRepository(impl: ViewedRecipesRepositoryImpl): ViewedRecipesRepository
}
