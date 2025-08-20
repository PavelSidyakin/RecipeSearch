package com.recipebook.datasource.remote.di

import com.recipebook.datasource.remote.SpoonacularRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideSpoonacularRemoteDataSource(
        @SpoonacularRetrofit
        retrofit: Retrofit,
    ): SpoonacularRemoteDataSource {
        return retrofit.create<SpoonacularRemoteDataSource>()
    }

    @SpoonacularRetrofit
    @Provides
    fun provideSpoonacularRemoteDataSourceRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com")
            .addConverterFactory(
                Json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType(),
                )
            )
            .build()
    }
}
