package com.recipebook.datasource.remote

import com.recipebook.datasource.remote.model.SpoonacularRecipeResponse
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
    ): SpoonacularRecipeResponse
}
