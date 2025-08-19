package com.recipebook.datasource.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.recipebook.datasource.local.db.entity.ViewedRecipeEntity

@Dao
interface ViewedRecipeDao {
    @Query("SELECT * FROM t_viewed_recipe")
    fun requestViewedRecipes(): List<ViewedRecipeEntity>

    @Query("SELECT * FROM t_viewed_recipe WHERE f_recipeId == :recipeId")
    fun requestViewedRecipe(
        recipeId: Int,
    ): ViewedRecipeEntity?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun updateOrInsertViewedRecipe(recipeEntity: ViewedRecipeEntity)
}
