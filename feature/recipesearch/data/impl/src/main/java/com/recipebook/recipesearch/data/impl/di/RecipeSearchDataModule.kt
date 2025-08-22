package com.recipebook.recipesearch.data.impl.di

import com.recipebook.recipesearch.data.api.RecipeSearchRepository
import com.recipebook.recipesearch.data.impl.RecipeSearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface RecipeSearchDataModule {

    @Binds
    fun bindRecipeSearchRepository(impl: RecipeSearchRepositoryImpl): RecipeSearchRepository
}
