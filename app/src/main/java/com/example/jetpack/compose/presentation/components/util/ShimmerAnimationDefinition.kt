package com.example.jetpack.compose.presentation.components.util

import androidx.compose.animation.core.*
import com.example.jetpack.compose.presentation.components.util.ShimmerAnimationDefinition.AnimationState.*

class ShimmerAnimationDefinition(
    val widthPx: Float,
    val heightPx: Float
) {
    enum class AnimationState {
        START, END
    }
    val xShimmerPropKey = FloatPropKey("xShimmer")
    val yShimmerPropKey = FloatPropKey("yShimmer")

    val shimmerTransitionDefinition = transitionDefinition<AnimationState> {
        state(START) {
          this[xShimmerPropKey] = 0f
          this[yShimmerPropKey] = 0f
        }
        state(END) {
          this[xShimmerPropKey] = widthPx
          this[yShimmerPropKey] = heightPx
        }

        transition(START, END) {
            xShimmerPropKey using infiniteRepeatable(
                animation = tween(
                    durationMillis = 1300,
                    delayMillis = 300,
                    easing = LinearEasing
                ),
            )
            yShimmerPropKey using infiniteRepeatable(
                animation = tween(
                    durationMillis = 1300,
                    delayMillis = 300,
                    easing = LinearEasing
                ),
            )
        }
    }
}