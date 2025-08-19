package com.recipebook.recipedetails.domain.impl.di

import com.recipebook.recipedetails.domain.api.RecipeDetailsInteractor
import com.recipebook.recipedetails.domain.impl.RecipeDetailsInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface RecipeDetailsSearchDomainModule {

    @Binds
    fun bindRecipeSearchInteractor(impl: RecipeDetailsInteractorImpl): RecipeDetailsInteractor
}
