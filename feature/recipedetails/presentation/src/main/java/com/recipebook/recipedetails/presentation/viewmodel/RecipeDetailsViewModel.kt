package com.recipebook.recipedetails.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipebook.recipedetails.domain.api.RecipeDetailsInteractor
import com.recipebook.recipedetails.domain.model.RecipeDetails
import com.recipebook.recipedetails.presentation.model.ErrorType
import com.recipebook.recipedetails.presentation.model.RecipeDetailsScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

@HiltViewModel(assistedFactory = RecipeDetailsViewModel.Factory::class)
internal class RecipeDetailsViewModel @AssistedInject constructor(
    @Assisted private val recipeId: Int,
    private val recipeDetailsInteractor: RecipeDetailsInteractor,
) : ViewModel() {
    private val stateFlowImpl = MutableStateFlow(RecipeDetailsScreenState.initialState)

    private var observeJob: Job? = null

    val stateFlow = stateFlowImpl.asStateFlow()

    fun onLaunch() {
        observeJob?.cancel()
        observeJob = recipeDetailsInteractor.observeRecipeDetails(recipeId)
            .onStart {
                try {
                    val recipeDetails = recipeDetailsInteractor.requestRecipeDetails(recipeId)
                    emit(recipeDetails)
                } catch (_: IOException) {
                    stateFlowImpl.update { it.copy(errorType = ErrorType.NETWORK) }
                } catch (_: Throwable) {
                    stateFlowImpl.update { it.copy(errorType = ErrorType.GENERAL) }
                }
            }
            .onEach { details ->
                updateDetails(details)
            }
            .launchIn(viewModelScope)
    }

    fun onDispose() {
        observeJob?.cancel()
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            recipeDetailsInteractor.setFavorite(recipeId, !stateFlowImpl.value.isFavorite)
        }
    }

    private fun updateDetails(recipeDetails: RecipeDetails) {
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
    }

    @AssistedFactory
    interface Factory {
        fun create(recipeId: Int): RecipeDetailsViewModel
    }
}
