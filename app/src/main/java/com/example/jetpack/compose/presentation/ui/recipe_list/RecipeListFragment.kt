package com.example.jetpack.compose.presentation.ui.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.jetpack.compose.R
import com.example.jetpack.compose.network.model.RecipeDtoMapper
import com.example.jetpack.compose.presentation.components.RecipeCard
import com.example.jetpack.compose.util.TAG
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment: Fragment() {
    val viewModel: RecipeListViewModel by viewModels()

    @Inject
    lateinit var mapper: RecipeDtoMapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val recipes = viewModel.recipes.value
                for (recipe in recipes) {
                    Log.d(TAG, "onCreateView: ${recipe.title}")
                }
                
                LazyColumn {
                    itemsIndexed(
                        items = recipes
                    ) { index, recipe ->
                        RecipeCard(recipe = recipe, onClick = { })
                    }
                }

            }
        }
    }

}