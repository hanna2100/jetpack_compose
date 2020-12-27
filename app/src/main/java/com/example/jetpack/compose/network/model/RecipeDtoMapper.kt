package com.example.jetpack.compose.network.model

import com.example.jetpack.compose.domain.model.Recipe
import com.example.jetpack.compose.domain.util.DomainMapper

class RecipeDtoMapper: DomainMapper<RecipeDto, Recipe> {
    override fun mapToDomainModel(domain: RecipeDto): Recipe {
        return Recipe(
            id = domain.pk,
            title = domain.title,
            featuredImage = domain.featuredImage,
            rating = domain.rating,
            publisher = domain.publisher,
            sourceUrl = domain.sourceUrl,
            description = domain.description,
            cookingInstructions = domain.cookingInstructions,
            ingredients = domain.ingredients?: listOf(),
            dateAdded = domain.dateAdded,
            dateUpdated = domain.dateUpdated,
        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeDto {
        return RecipeDto(
            title = domainModel.title,
            featuredImage = domainModel.featuredImage,
            rating = domainModel.rating,
            publisher = domainModel.publisher,
            sourceUrl = domainModel.sourceUrl,
            description = domainModel.description,
            cookingInstructions = domainModel.cookingInstructions,
            ingredients = domainModel.ingredients,
            dateAdded = domainModel.dateAdded,
            dateUpdated = domainModel.dateUpdated,
        )
    }

    fun toDomainList(initial: List<RecipeDto>): List<Recipe> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Recipe>): List<RecipeDto> {
        return initial.map { mapFromDomainModel(it) }
    }
}