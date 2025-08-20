package com.recipebook.viewedrecipes.domain.impl.di

import com.recipebook.viewedrecipes.domain.api.ViewedRecipesInteractor
import com.recipebook.viewedrecipes.domain.impl.ViewedRecipesInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface ViewedRecipesDomainModule {

    @Binds
    fun bindViewedRecipesInteractor(impl: ViewedRecipesInteractorImpl): ViewedRecipesInteractor
}
