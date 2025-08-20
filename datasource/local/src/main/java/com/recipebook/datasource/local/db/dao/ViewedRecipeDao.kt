package com.recipebook.datasource.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.recipebook.datasource.local.db.entity.ViewedRecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ViewedRecipeDao {
    @Query("SELECT * FROM t_viewed_recipe")
    fun requestViewedRecipes(): List<ViewedRecipeEntity>

    @Query("SELECT * FROM t_viewed_recipe WHERE f_recipeId == :recipeId")
    fun requestViewedRecipe(
        recipeId: Int,
    ): ViewedRecipeEntity?

    @Query("SELECT * FROM t_viewed_recipe WHERE f_recipeId == :recipeId")
    fun observeViewedRecipe(
        recipeId: Int,
    ): Flow<ViewedRecipeEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun updateOrInsertViewedRecipe(recipeEntity: ViewedRecipeEntity)

    @Query(
        """
        UPDATE t_viewed_recipe
        SET f_isFavorite = :isFavorite
        WHERE f_recipeId == :recipeId
        """
    )
    suspend fun updateIsFavorite(recipeId: Int, isFavorite: Boolean)
}
