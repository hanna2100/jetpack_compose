package com.example.jetpack.compose.presentation.components

import androidx.compose.animation.transition
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.WithConstraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpack.compose.presentation.components.util.ShimmerAnimationDefinition
import com.example.jetpack.compose.presentation.components.util.ShimmerAnimationDefinition.AnimationState
import com.example.jetpack.compose.presentation.components.util.ShimmerAnimationDefinition.AnimationState.*

@Composable
fun LoadingRecipeListShimmer(
        imageHeight : Dp,
        padding: Dp = 16.dp
) {
    WithConstraints() {
        val cardAnimationDefinition = ShimmerAnimationDefinition(
            widthPx = maxWidth.value,
            heightPx = imageHeight.value
        )
        val cardShimmerTranslateAnim = transition(
            definition = cardAnimationDefinition.shimmerTransitionDefinition,
            initState = START,
            toState = END
        )
        val colors = listOf(
            Color.LightGray.copy(alpha = 0.9f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.9f)
        )
        val xCardShimmer = cardShimmerTranslateAnim[cardAnimationDefinition.xShimmerPropKey]
        val yCardShimmer = cardShimmerTranslateAnim[cardAnimationDefinition.yShimmerPropKey]
        ShimmerRecipeCardItem(colors = colors, cardHeight = 250.dp )
    }
}