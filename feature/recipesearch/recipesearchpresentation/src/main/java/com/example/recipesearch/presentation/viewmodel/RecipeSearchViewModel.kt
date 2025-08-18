package com.example.recipesearch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class RecipeSearchViewModel @Inject constructor(

) : ViewModel() {
    private val stateFlowImpl = MutableStateFlow(RecipeSearchScreenState.initialState)

    val stateFlow = stateFlowImpl.asStateFlow()

    fun onLaunch() {
        stateFlowImpl.update { it.copy(text = "Hello from VM") }
    }

    fun onDispose() {

    }
}
