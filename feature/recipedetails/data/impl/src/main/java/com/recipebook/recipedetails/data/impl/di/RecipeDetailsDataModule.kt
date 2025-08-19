package com.recipebook.recipedetails.data.impl.di

import com.recipebook.recipedetails.data.api.RecipeDetailsLocalRepository
import com.recipebook.recipedetails.data.api.RecipeDetailsRemoteRepository
import com.recipebook.recipedetails.data.impl.RecipeDetailsLocalRepositoryImpl
import com.recipebook.recipedetails.data.impl.RecipeDetailsRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface RecipeDetailsDataModule {

    @Binds
    fun bindRecipeSearchRepository(impl: RecipeDetailsRemoteRepositoryImpl): RecipeDetailsRemoteRepository

    @Binds
    fun bindRecipeDetailsLocalRepository(impl: RecipeDetailsLocalRepositoryImpl): RecipeDetailsLocalRepository
}
