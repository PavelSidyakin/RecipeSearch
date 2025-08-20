package com.recipebook.viewedrecipes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipebook.viewedrecipes.domain.api.ViewedRecipesInteractor
import com.recipebook.viewedrecipes.presentation.model.ViewedRecipesItemState
import com.recipebook.viewedrecipes.presentation.model.ViewedRecipesScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ViewedRecipesViewModel @Inject constructor(
    private val viewedRecipesInteractor: ViewedRecipesInteractor,
) : ViewModel() {
    private val stateFlowImpl = MutableStateFlow(ViewedRecipesScreenState.initialState)
    private val externalEventsFlowImpl = MutableSharedFlow<ViewedRecipesExternalEvent>()

    private var updateDataJob: Job? = null

    val stateFlow = stateFlowImpl.asStateFlow()
    val externalEventsFlow = externalEventsFlowImpl.asSharedFlow()

    fun onLaunch() {
        updateData()
    }

    fun onDispose() {
        updateDataJob?.cancel()
    }

    fun onShowFavoritesOnlySwitchChange(isChecked: Boolean) {
        stateFlowImpl.update { it.copy(isFavoriteFilter = isChecked) }
        updateData()
    }

    private fun updateData() {
        updateDataJob?.cancel()
        updateDataJob = viewModelScope.launch {
            val viewedRecipes = when (stateFlow.value.isFavoriteFilter) {
                false -> viewedRecipesInteractor.requestViewedRecipes()
                true -> viewedRecipesInteractor.requestFavoriteViewedRecipes()
            }

            stateFlowImpl.update { state ->
                state.copy(
                    items = viewedRecipes.map { viewedRecipe ->
                        ViewedRecipesItemState(
                            recipeId = viewedRecipe.recipeId,
                            imageUrl = viewedRecipe.recipeImageUrl,
                            name = viewedRecipe.recipeName,
                            description = viewedRecipe.description,
                            price = viewedRecipe.price,
                            isFavorite = viewedRecipe.isFavorite,
                        )
                    }
                )
            }
        }
    }

    fun onRecipeClick(recipeId: Int) {
        viewModelScope.launch {
            externalEventsFlowImpl.emit(ViewedRecipesExternalEvent.OnRecipeClicked(recipeId))
        }
    }
}
