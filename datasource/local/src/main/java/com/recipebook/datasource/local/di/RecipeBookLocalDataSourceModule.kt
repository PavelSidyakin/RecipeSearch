package com.recipebook.datasource.local.di

import android.content.Context
import androidx.room.Room
import com.recipebook.datasource.local.db.RecipeBookDatabase
import com.recipebook.datasource.local.db.dao.ViewedRecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RecipeBookLocalDataSourceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): RecipeBookDatabase {
        return Room.databaseBuilder(context, RecipeBookDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    @Provides
    fun provideViewedRecipeDao(recipeBookDatabase: RecipeBookDatabase): ViewedRecipeDao {
        return recipeBookDatabase.viewedRecipeDaoDao()
    }

    private const val DATABASE_NAME = "recipe_book_db"
}
