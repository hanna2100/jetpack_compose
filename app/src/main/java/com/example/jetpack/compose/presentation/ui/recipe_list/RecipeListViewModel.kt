package com.example.jetpack.compose.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.compose.domain.model.Recipe
import com.example.jetpack.compose.repository.RecipeRepository
import com.example.jetpack.compose.util.PAGE_SIZE
import com.example.jetpack.compose.util.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Named

class RecipeListViewModel
@ViewModelInject
constructor(
        private val recipeRepository: RecipeRepository,
        private @Named("auth_token") val token: String,
): ViewModel() {
    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    var categoryScrollPosition: Float = 0f
    val loading = mutableStateOf(false)
    val page = mutableStateOf(1)
    private var recipeListScrollPosition = 0

    init {
        newSearch()
    }

    fun newSearch() {
        viewModelScope.launch {
            loading.value = true
            resetSearchState()
            val result = recipeRepository.search(
                token = token,
                page = 1,
                query = query.value
            )
            recipes.value = result
            loading.value= false
        }
    }

    fun nextPage() {
        viewModelScope.launch {
            if((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
                loading.value = true
                incrementPage()
                Log.d(TAG, "next page: triggered: ${page.value}")
                delay(1000)

                if(page.value > 1) {
                    val result = recipeRepository.search(
                        token = token,
                        page = page.value,
                        query = query.value
                    )
                    Log.d(TAG, "next page: $result")
                    appendRecipes(result)
                }
                loading.value = false
            }
        }
    }

    private fun appendRecipes(recipe: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipe)
        this.recipes.value = current
    }

    private fun incrementPage(){
        page.value = page.value +1
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        recipeListScrollPosition = position
    }

    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if(selectedCategory.value?.value != query.value) {
            clearSelectedCategory()
        }
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    fun onChangedCategoryScrollPosition(position: Float) {
        categoryScrollPosition = position
    }
}