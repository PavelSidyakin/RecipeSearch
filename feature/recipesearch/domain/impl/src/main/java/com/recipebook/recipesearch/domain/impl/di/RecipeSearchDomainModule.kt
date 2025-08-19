package com.recipebook.recipesearch.domain.impl.di

import com.recipebook.recipesearch.domain.api.RecipeSearchInteractor
import com.recipebook.recipesearch.domain.impl.RecipeSearchInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface RecipeSearchDomainModule {

    @Binds
    fun bindRecipeSearchInteractor(impl: RecipeSearchInteractorImpl): RecipeSearchInteractor
}
