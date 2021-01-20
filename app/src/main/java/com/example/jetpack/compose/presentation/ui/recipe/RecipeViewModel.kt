package com.example.jetpack.compose.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.compose.domain.model.Recipe
import com.example.jetpack.compose.presentation.ui.recipe.RecipeEvent.*
import com.example.jetpack.compose.repository.RecipeRepository
import com.example.jetpack.compose.util.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Named

const val STATE_KEY_RECIPE = "state.recipe.key.recipeId"

class RecipeViewModel
@ViewModelInject
constructor(
    private val recipeRepository: RecipeRepository,
    private @Named("auth_token") val token: String,
    @Assisted private val state: SavedStateHandle,
): ViewModel() {
    val recipe: MutableState<Recipe?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

    init {
        // 프로세스가 죽었을때 복구
        state.get<Int>(STATE_KEY_RECIPE)?.let { recipeId ->
            onTriggerEvent(GetRecipeEvent(recipeId))
        }
    }

    fun onTriggerEvent(event: RecipeEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is GetRecipeEvent -> {
                        if(recipe.value == null) {
                            getRecipe(event.id)
                        }
                    }
                }
            }catch (e: Exception) {
                Log.e(TAG, "onTriggerEvent: Exception ${e}, ${e.cause}")
            }
        }
    }

    private suspend fun getRecipe(id: Int) {
        loading.value = true
        delay(1000)
        val recipe = recipeRepository.get(token, id)
        this.recipe.value = recipe
        state.set(STATE_KEY_RECIPE, recipe.id)
        loading.value = false
    }
}