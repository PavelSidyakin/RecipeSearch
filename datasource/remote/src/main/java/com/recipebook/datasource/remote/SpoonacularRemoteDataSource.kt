package com.recipebook.datasource.remote

import com.recipebook.datasource.remote.model.SpoonacularRecipeResponse
import com.recipebook.datasource.remote.model.SpoonacularRecipeSortDirection
import com.recipebook.datasource.remote.model.SpoonacularRecipeSortOption
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SpoonacularRemoteDataSource {

    @Headers("x-api-key: ${BuildConfig.SPOONACULAR_API_KEY}")
    @GET("recipes/complexSearch?addRecipeInformation=true")
    suspend fun requestRecipes(
        @Query("query")
        query: String,

        @Query("offset")
        offset: Int,

        @Query("number")
        number: Int,

        @Query("sort")
        sortOption: SpoonacularRecipeSortOption,

        @Query("sortDirection")
        sortDirection: SpoonacularRecipeSortDirection,
    ): SpoonacularRecipeResponse
}
