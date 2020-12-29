package com.example.jetpack.compose.presentation.ui.recipe_list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class RecipeListViewModel
@ViewModelInject
constructor(
    private val randomString: String
): ViewModel() {
    init {
        println("ViewModel: $randomString")
    }

}