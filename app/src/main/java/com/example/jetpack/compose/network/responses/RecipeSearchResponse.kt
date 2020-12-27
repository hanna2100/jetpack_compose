package com.example.jetpack.compose.network.responses

import com.example.jetpack.compose.network.model.RecipeDto
import com.google.gson.annotations.SerializedName

class RecipeSearchResponse (
        @SerializedName("count")
    var count: Int,

        @SerializedName("results")
    var recipes: List<RecipeDto>,
)