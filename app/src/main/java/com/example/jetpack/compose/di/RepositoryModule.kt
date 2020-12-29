package com.example.jetpack.compose.di

import com.example.jetpack.compose.network.RecipeService
import com.example.jetpack.compose.network.model.RecipeDtoMapper
import com.example.jetpack.compose.repository.RecipeRepository
import com.example.jetpack.compose.repository.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
            recipeService: RecipeService,
            recipeDtoMapper: RecipeDtoMapper
    ): RecipeRepository {
        return RecipeRepositoryImpl(recipeService, recipeDtoMapper)
    }
}