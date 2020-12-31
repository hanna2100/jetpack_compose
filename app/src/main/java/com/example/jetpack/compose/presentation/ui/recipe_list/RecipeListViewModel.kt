package com.example.jetpack.compose.presentation.ui.recipe_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.compose.domain.model.Recipe
import com.example.jetpack.compose.repository.RecipeRepository
import kotlinx.coroutines.launch
import javax.inject.Named

class RecipeListViewModel
@ViewModelInject
constructor(
        private val recipeRepository: RecipeRepository,
        private @Named("auth_token") val token: String,
): ViewModel() {
    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("con")

    init {
        newSearch("")
    }

    fun newSearch(query: String) {
        viewModelScope.launch {
            val result = recipeRepository.search(
                    token = token,
                    page = 1,
                    query = query,
            )
            recipes.value = result
        }
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }
}
