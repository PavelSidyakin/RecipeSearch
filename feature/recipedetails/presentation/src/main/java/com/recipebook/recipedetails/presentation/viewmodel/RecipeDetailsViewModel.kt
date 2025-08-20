package com.recipebook.recipedetails.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipebook.recipedetails.domain.api.RecipeDetailsInteractor
import com.recipebook.recipedetails.presentation.model.ErrorType
import com.recipebook.recipedetails.presentation.model.RecipeDetailsScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

@HiltViewModel(assistedFactory = RecipeDetailsViewModel.Factory::class)
internal class RecipeDetailsViewModel @AssistedInject constructor(
    @Assisted private val recipeId: Int,
    private val recipeDetailsInteractor: RecipeDetailsInteractor,
) : ViewModel() {
    private val stateFlowImpl = MutableStateFlow(RecipeDetailsScreenState.initialState)
    val stateFlow = stateFlowImpl.asStateFlow()

    fun onLaunch() {
        viewModelScope.launch {
            try {
                val recipeDetails = recipeDetailsInteractor.requestRecipeDetails(recipeId)
                stateFlowImpl.update { state ->
                    state.copy(
                        errorType = null,
                        recipeId = recipeId,
                        recipeName = recipeDetails.recipeName,
                        recipeImageUrl = recipeDetails.recipeImageUrl,
                        ingredients = recipeDetails.ingredients,
                        instructions = recipeDetails.instructions,
                        sourceWebsiteLink = recipeDetails.sourceWebsiteLink,
                        isFavorite = recipeDetails.isFavorite,
                        price = recipeDetails.price,
                    )
                }
            } catch (_: IOException) {
                stateFlowImpl.update { it.copy(errorType = ErrorType.NETWORK) }
            } catch (_: Throwable) {
                stateFlowImpl.update { it.copy(errorType = ErrorType.GENERAL) }
            }
        }
    }

    fun onDispose() {
    }

    fun onFavoriteClicked() {

    }

    @AssistedFactory
    interface Factory {
        fun create(recipeId: Int): RecipeDetailsViewModel
    }
}
