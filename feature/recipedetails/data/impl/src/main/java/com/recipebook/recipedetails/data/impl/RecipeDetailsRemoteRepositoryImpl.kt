package com.recipebook.recipedetails.data.impl

import com.recipebook.datasource.remote.SpoonacularRemoteDataSource
import com.recipebook.datasource.remote.model.SpoonacularRecipeInformationResponse
import com.recipebook.recipedetails.data.api.RecipeDetailsRemoteRepository
import com.recipebook.recipedetails.domain.model.RecipeDetails
import javax.inject.Inject

internal class RecipeDetailsRemoteRepositoryImpl @Inject constructor(
    private val spoonacularRemoteDataSource: SpoonacularRemoteDataSource,
) : RecipeDetailsRemoteRepository {
    override suspend fun requestRecipeDetails(recipeId: Int): RecipeDetails {
//        return RecipeDetails(
//            recipeId = recipeId,
//            recipeName = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
//            recipeImageUrl = "https://img.spoonacular.com/recipes/716429-556x370.jpg",
//            ingredients = "2 tbsp grated cheese (I used romano)\n1 tbsp butter",
//            instructions = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs might be a good recipe to expand your main course repertoire. One portion of this dish contains approximately <b>19g of protein </b>,  <b>20g of fat </b>, and a total of  <b>584 calories </b>. For  <b>$1.63 per serving </b>, this recipe  <b>covers 23% </b> of your daily requirements of vitamins and minerals. This recipe serves 2. It is brought to you by fullbellysisters.blogspot.com. 209 people were glad they tried this recipe. A mixture of scallions, salt and pepper, white wine, and a handful of other ingredients are all it takes to make this recipe so scrumptious. From preparation to the plate, this recipe takes approximately  <b>45 minutes </b>. All things considered, we decided this recipe  <b>deserves a spoonacular score of 83% </b>. This score is awesome. If you like this recipe, take a look at these similar recipes: <a href=\"https://spoonacular.com/recipes/cauliflower-gratin-with-garlic-breadcrumbs-318375\">Cauliflower Gratin with Garlic Breadcrumbs</a>, < href=\"https://spoonacular.com/recipes/pasta-with-cauliflower-sausage-breadcrumbs-30437\">Pasta With Cauliflower, Sausage, & Breadcrumbs</a>, and <a href=\"https://spoonacular.com/recipes/pasta-with-roasted-cauliflower-parsley-and-breadcrumbs-30738\">Pasta With Roasted Cauliflower, Parsley, And Breadcrumbs</a>.",
//            sourceWebsiteLink = "http://fullbellysisters.blogspot.com/2012/06/pasta-with-garlic-scallions-cauliflower.html",
//            isFavorite = false,
//            price = 80f,
//        )
//
        return spoonacularRemoteDataSource.requestRecipeInformation(recipeId).toRecipeDetails()
    }

    private fun SpoonacularRecipeInformationResponse.toRecipeDetails(): RecipeDetails {
        return RecipeDetails(
            recipeId = id,
            recipeName = title,
            recipeImageUrl = imageUrl,
            ingredients = ingredients.joinToString("\n") { it.ingredientDescription },
            instructions = summary,
            sourceWebsiteLink = sourceWebsiteLink,
            isFavorite = false,
            price = pricePerServing,
        )
    }
}
