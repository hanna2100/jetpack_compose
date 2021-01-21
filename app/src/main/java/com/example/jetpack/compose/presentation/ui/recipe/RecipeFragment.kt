package com.example.jetpack.compose.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.dispatch.AndroidUiDispatcher.Companion.Main
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.jetpack.compose.presentation.components.CircularIndeterminateProgressBar
import com.example.jetpack.compose.presentation.components.DefaultSnackbar
import com.example.jetpack.compose.presentation.components.RecipeView
import com.example.jetpack.compose.presentation.components.util.SnackbarController
import com.example.jetpack.compose.presentation.theme.AppTheme
import com.example.jetpack.compose.presentation.ui.BaseApplication
import com.example.jetpack.compose.presentation.ui.recipe.RecipeEvent.*
import com.example.jetpack.compose.presentation.ui.recipe_list.RecipeListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment: Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val snackbarController = SnackbarController(lifecycleScope)

    private val viewModel: RecipeViewModel by viewModels()

    private var recipeId: MutableState<Int> = mutableStateOf(-1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let { recipeId ->
            viewModel.onTriggerEvent(GetRecipeEvent(recipeId))
        }
    }

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading = viewModel.loading.value
                val recipe = viewModel.recipe.value

                val scaffoldState = rememberScaffoldState()

                AppTheme(darkTheme = application.isDark.value) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            if (loading && recipe == null) {
                                Text("Loading...")
                            } else {
                               recipe?.let {
                                   if (it.id == 1) {
                                       snackbarController.showSnackbar(
                                           scaffoldState = scaffoldState,
                                           message = "An error occurred with this recipe.",
                                           actionLabel = "Ok"
                                       )
                                   } else {
                                       RecipeView(recipe = it)
                                   }
                               }
                            }
                            CircularIndeterminateProgressBar(
                                isDisplayed = loading
                            )
                            DefaultSnackbar(
                                snackbarHostState = scaffoldState.snackbarHostState,
                                onDismiss = {
                                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                },
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }
                    }
                }
            }
        }
    }
}