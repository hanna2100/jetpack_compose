package com.example.jetpack.compose.presentation.components

import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.jetpack.compose.presentation.ui.recipe_list.FoodCategory
import com.example.jetpack.compose.presentation.ui.recipe_list.getAllFoodCategories

@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    scrollPosition: Float,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChanged: (String) -> Unit,
    onChangedCategoryScrollPosition: (Float) -> Unit,
    onToggleTheme: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.surface,
        elevation = 8.dp,
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField (
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(8.dp),
                    value = query,
                    label = {
                        Text(text = "Search")
                    },
                    onValueChange = { newValue ->
                        onQueryChanged(newValue)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    leadingIcon = {
                        Icon(Icons.Filled.Search)
                    },
                    onImeActionPerformed = { action, softKeyboardController ->
                        if(action == ImeAction.Search) {
                            onExecuteSearch()
                            softKeyboardController?.hideSoftwareKeyboard()
                        }
                    },
                    textStyle = MaterialTheme.typography.button,
                    backgroundColor = MaterialTheme.colors.surface,
                )
                ConstraintLayout(
                        modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    val menu = createRef()
                    IconButton(
                            onClick = onToggleTheme,
                            modifier = Modifier.constrainAs(menu) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    ) {
                        Icon(Icons.Filled.MoreVert)
                    }
                }
            }
            val scrollState = rememberScrollState()
            ScrollableRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 8.dp),
                scrollState = scrollState
            ) {
                scrollState.scrollTo(scrollPosition)
                for(category in getAllFoodCategories()) {
                    FoodCategoryChip(
                        category = category.value,
                        isSelected = selectedCategory == category,
                        onSelectedCategoryChanged = {
                            onSelectedCategoryChanged(it)
                            onChangedCategoryScrollPosition(scrollState.value)
                        },
                        onExecuteSearch = {
                            onExecuteSearch()
                        }
                    )
                }
            }
        }
    }
}