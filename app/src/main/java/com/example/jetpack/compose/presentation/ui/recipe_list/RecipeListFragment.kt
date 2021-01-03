package com.example.jetpack.compose.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.jetpack.compose.presentation.components.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment: Fragment() {
    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val recipes = viewModel.recipes.value
                val query = viewModel.query.value
                val selectedCategory = viewModel.selectedCategory.value
                val loading = viewModel.loading.value
                Column {
                    SearchAppBar(
                        query = query,
                        onQueryChanged = viewModel::onQueryChanged,
                        onExecuteSearch = viewModel::newSearch,
                        scrollPosition = viewModel.categoryScrollPosition,
                        selectedCategory = selectedCategory,
                        onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                        onChangedCategoryScrollPosition = viewModel::onChangedCategoryScrollPosition
                    )

                    Row(
                        modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val state = remember { mutableStateOf(HeartAnimationDefinition.HeartButtonState.IDLE) }
                        AnimationHeartButton(
                            modifier = Modifier,
                            buttonState = state,
                            onToggle = { state.value = if(state.value == HeartAnimationDefinition.HeartButtonState.IDLE) {
                                    HeartAnimationDefinition.HeartButtonState.ACTIVE
                                } else {
                                    HeartAnimationDefinition.HeartButtonState.IDLE
                                }
                            }
                        )
                    }

                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        LazyColumn {
                            itemsIndexed(
                                items = recipes
                            ) { index, recipe ->
                                RecipeCard(recipe = recipe, onClick = { })
                            }
                        }
                        CircularIndeterminateProgressBar(isDisplayed = loading)
                    }
                }
            }
        }
    }

}