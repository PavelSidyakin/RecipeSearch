package com.recipebook.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.recipebook.datasource.local.db.dao.ViewedRecipeDao
import com.recipebook.datasource.local.db.entity.ViewedRecipeEntity

@Database(
    version = 1,
    entities = [
        ViewedRecipeEntity::class,
    ],
)
internal abstract class RecipeBookDatabase : RoomDatabase() {
    abstract fun viewedRecipeDaoDao(): ViewedRecipeDao
}
