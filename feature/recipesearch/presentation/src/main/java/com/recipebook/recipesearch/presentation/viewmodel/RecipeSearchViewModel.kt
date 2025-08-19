package com.recipebook.recipesearch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.recipebook.recipesearch.domain.model.SearchResultSortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

private const val SEARCH_DEBOUNCE_TIMEOUT_MS = 1000L

@HiltViewModel
internal class RecipeSearchViewModel @Inject constructor(
    private val recipeSearchPagingSourceFactory: RecipeSearchPagingSource.Factory,
) : ViewModel() {
    private val stateFlowImpl = MutableStateFlow(RecipeSearchScreenState.initialState)
    private val searchTextFlow = MutableStateFlow("")
    private val sortOptionFlow = MutableStateFlow(SearchResultSortOption.CALORIES_ASCENDING)
    private val pagingListFlowImpl = MutableSharedFlow<PagingData<RecipeSearchListItemState>>()

    private var searchTextRequestJob: Job? = null

    val stateFlow = stateFlowImpl.asStateFlow()
    val pagingListFlow: Flow<PagingData<RecipeSearchListItemState>> = pagingListFlowImpl.asSharedFlow()

    fun onLaunch() {
        searchTextRequestJob?.cancel()
        searchTextRequestJob = combine(
            searchTextFlow
                .debounce(SEARCH_DEBOUNCE_TIMEOUT_MS.toDuration(DurationUnit.MILLISECONDS))
                .filter { it.isNotBlank() },
            sortOptionFlow,
        ) { text, sortOption -> text to sortOption }

            .flatMapLatest { (text, sortOption) ->
                Pager(
                    config = PagingConfig(
                        pageSize = RECIPE_SEARCH_PAGE_SIZE,
                        initialLoadSize = RECIPE_SEARCH_PAGE_SIZE,
                    ),
                    pagingSourceFactory = {
                        recipeSearchPagingSourceFactory.create(text, sortOption)
                    },
                ).flow
            }
            .onEach { data ->
                pagingListFlowImpl.emit(data)
            }
            .launchIn(viewModelScope)
    }

    fun onDispose() {
        searchTextRequestJob?.cancel()
    }

    fun onSearchTextChanged(text: String) {
        stateFlowImpl.update { it.copy(searchText = text) }
        searchTextFlow.tryEmit(text)
    }

    fun onLazyPagingItemsReady(lazyPagingItems: LazyPagingItems<RecipeSearchListItemState>) {
        stateFlowImpl.update { it.copy(lazyPagingItems = lazyPagingItems) }
    }

    fun onCaloriesSortClicked() {
        stateFlowImpl.update { currentState ->
            currentState.copy(
                sortOption = when (currentState.sortOption) {
                    SearchResultSortOption.CALORIES_ASCENDING -> SearchResultSortOption.CALORIES_DESCENDING
                    SearchResultSortOption.CALORIES_DESCENDING -> SearchResultSortOption.CALORIES_ASCENDING
                }.also { sortOptionFlow.tryEmit(it) },
            )
        }
    }
}
