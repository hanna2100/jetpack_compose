package com.example.jetpack.compose.repository

import com.example.jetpack.compose.domain.model.Recipe
import com.example.jetpack.compose.network.RecipeService
import com.example.jetpack.compose.network.model.RecipeDtoMapper
import javax.inject.Inject

class RecipeRepositoryImpl(
        private val service: RecipeService,
        private val mapper: RecipeDtoMapper
) : RecipeRepository {
    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        return mapper.toDomainList(service.search(token, page, query).recipes)
    }

    override suspend fun get(token: String, id: Int): Recipe {
        return mapper.mapToDomainModel(service.get(token, id))
    }

}