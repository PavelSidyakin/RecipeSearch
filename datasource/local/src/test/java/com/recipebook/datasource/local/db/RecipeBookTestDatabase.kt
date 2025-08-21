package com.recipebook.datasource.local.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider

internal object RecipeBookTestDatabase {

    fun createDatabase(): RecipeBookDatabase {
        return Room.inMemoryDatabaseBuilder(
            context = ApplicationProvider.getApplicationContext<Context>(),
            klass = RecipeBookDatabase::class.java,
        ).allowMainThreadQueries()
            .build()
    }
}
