package com.example.jetpack.compose.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.dispatch.AndroidUiDispatcher.Companion.Main
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.jetpack.compose.presentation.ui.recipe.RecipeEvent.*
import com.example.jetpack.compose.presentation.ui.recipe_list.RecipeListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeFragment: Fragment() {

    private val viewModel: RecipeViewModel by viewModels()

    private var recipeId: MutableState<Int> = mutableStateOf(-1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let { recipeId ->
            viewModel.onTriggerEvent(GetRecipeEvent(recipeId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading = viewModel.loading.value
                val recipe = viewModel.recipe.value
                Column (modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = recipe?.let { "selected recipe title = ${recipe.title}" }?: "Loading...",
                        style = TextStyle(
                            fontSize = TextUnit.Companion.Sp(21)
                        )
                    )
                }
            }
        }
    }
}