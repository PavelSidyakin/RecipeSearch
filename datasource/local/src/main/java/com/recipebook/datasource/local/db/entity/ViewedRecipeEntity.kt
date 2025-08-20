package com.recipebook.datasource.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "t_viewed_recipe")
data class ViewedRecipeEntity(
    @PrimaryKey
    @ColumnInfo(name = "f_recipeId")
    val recipeId: Int,

    @ColumnInfo(name = "f_recipeName")
    val recipeName: String,

    @ColumnInfo(name = "f_recipeImageUrl")
    val recipeImageUrl: String,

    @ColumnInfo(name = "f_ingredients")
    val ingredients: String,

    @ColumnInfo(name = "f_instructions")
    val instructions: String,

    @ColumnInfo(name = "f_sourceWebsiteLink")
    val sourceWebsiteLink: String,

    @ColumnInfo(name = "f_isFavorite")
    val isFavorite: Boolean,

    @ColumnInfo(name = "f_price")
    val price: Float,
)
