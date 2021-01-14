package com.example.jetpack.compose.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.ui.tooling.preview.Preview
import com.example.jetpack.compose.presentation.components.*
import com.example.jetpack.compose.presentation.theme.AppTheme
import com.example.jetpack.compose.presentation.ui.BaseApplication
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment: Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
//                val isShowing = remember{ mutableStateOf(false) }
                val snackbarHostState = remember { SnackbarHostState() }
                Column {
                    Button(
                            onClick = {
                                lifecycleScope.launch {
                                    snackbarHostState.showSnackbar(
                                            message = "Hey look a snackbar",
                                            actionLabel = "Hide",
                                            duration = SnackbarDuration.Short
                                    )
                                }
                            }
                    ) {
                        Text("Show snack bar")
                    }
                    DecoupledSnackbarDemo(snackbarHostState = snackbarHostState)
                }
//                SnackBarDemo(
//                        isShowing = isShowing.value,
//                        onHideSnackBar = {
//                            isShowing.value = false
//                        },
//                )

//                AppTheme(darkTheme = application.isDark.value) {
//                    val recipes = viewModel.recipes.value
//                    val query = viewModel.query.value
//                    val selectedCategory = viewModel.selectedCategory.value
//                    val loading = viewModel.loading.value
//
//                    Scaffold(
//                            topBar = {
//                                SearchAppBar(
//                                        query = query,
//                                        onQueryChanged = viewModel::onQueryChanged,
//                                        onExecuteSearch = viewModel::newSearch,
//                                        scrollPosition = viewModel.categoryScrollPosition,
//                                        selectedCategory = selectedCategory,
//                                        onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
//                                        onChangedCategoryScrollPosition = viewModel::onChangedCategoryScrollPosition,
//                                        onToggleTheme = {
//                                            application.toggleLightTheme()
//                                        }
//                                )
//                            },
//                            bottomBar = {
//                                MyBottomBar()
//                            },
//                            drawerContent = {
//                                MyDrawer()
//                            }
//                    ) {
//                        Box(
//                                modifier = Modifier
//                                        .fillMaxWidth()
//                                        .background(color = MaterialTheme.colors.background)
//                        ) {
//                            if(loading) {
//                                LoadingRecipeListShimmer(imageHeight = 250.dp)
//                            } else {
//                                LazyColumn {
//                                    itemsIndexed(
//                                            items = recipes
//                                    ) { index, recipe ->
//                                        RecipeCard(recipe = recipe, onClick = { })
//                                    }
//                                }
//                            }
//                            CircularIndeterminateProgressBar(isDisplayed = loading)
//                        }
//                    }
//                }
            }
        }
    }
}
@Composable
fun heart() {
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
}


@Preview
@Composable
fun GradientDemo() {
    val colors = listOf(
        Color.Blue,
        Color.Red,
        Color.Blue
    )
    val brush = Brush.linearGradient(
        colors,
        start = Offset(200f, 200f),
        end = Offset(400f, 400f)
    )
    Surface(
        shape = MaterialTheme.shapes.small
    ) {
        Spacer(
            modifier = Modifier
            .fillMaxSize()
            .background(brush = brush)
        )
    }
}

@Composable
fun MyBottomBar() {
    BottomNavigation(
            elevation = 12.dp
    ) {
        BottomNavigationItem(
                icon = { Icon(Icons.Default.BrokenImage) },
                selected = false,
                onClick = {

                }
        )
        BottomNavigationItem(
                icon = { Icon(Icons.Default.Search) },
                selected = false,
                onClick = {

                }
        )
        BottomNavigationItem(
                icon = { Icon(Icons.Default.AccountBalanceWallet) },
                selected = false,
                onClick = {

                }
        )
    }
}

@Composable
fun MyDrawer() {
    Column() {
        Text("Item1")
        Text("Item2")
        Text("Item3")
        Text("Item4")
        Text("Item5")
    }
}

@Composable
fun SnackBarDemo(
        isShowing: Boolean,
        onHideSnackBar: () -> Unit,
){
    if (isShowing) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val snackBar = createRef()
            Snackbar(
                    modifier = Modifier.constrainAs(snackBar) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    action = {
                        Text(
                                text = "hide",
                                modifier = Modifier.clickable(
                                        onClick = onHideSnackBar,
                                ),
                                style = MaterialTheme.typography.h5
                        )
                    }
            ) {
                Text("Hey, Look a snack bar!")
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DecoupledSnackbarDemo(
        snackbarHostState: SnackbarHostState, // 애니메이션 및 큐 시스템 관리
){
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val snackBar = createRef()
        SnackbarHost(
                modifier = Modifier.constrainAs(snackBar) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                hostState = snackbarHostState,
                snackbar = {
                    Snackbar (
                        action = {
                            TextButton(
                                    onClick = {
                                        snackbarHostState.currentSnackbarData?.dismiss()
                                    }
                            ) {
                                Text(
                                        text = snackbarHostState.currentSnackbarData?.actionLabel?: "",
                                        style = TextStyle(color = Color.White)
                                )
                            }
                        }
                    ) {
                        Text(snackbarHostState.currentSnackbarData?.message?: "")
                    }
                }
        )
    }
}