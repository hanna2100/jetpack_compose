package com.example.jetpack.compose.presentation.components

import androidx.compose.animation.ColorPropKey
import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.TransitionState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import androidx.compose.animation.transition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.jetpack.compose.R
import com.example.jetpack.compose.presentation.components.HeartAnimationDefinition.heartSize
import com.example.jetpack.compose.presentation.components.HeartAnimationDefinition.heartTransitionDefinition
import com.example.jetpack.compose.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Composable
fun AnimationHeartButton(
        modifier: Modifier,
        buttonState: MutableState<HeartAnimationDefinition.HeartButtonState>,
        onToggle: () -> Unit,
) {
    val toState = if(buttonState.value == HeartAnimationDefinition.HeartButtonState.IDLE) {
        HeartAnimationDefinition.HeartButtonState.ACTIVE
    } else {
        HeartAnimationDefinition.HeartButtonState.IDLE
    }

    val state = transition(
        definition = heartTransitionDefinition,
        initState = buttonState.value,
        toState = toState
    )
    HeartButton(
        modifier = modifier,
        buttonState = buttonState,
        state = state,
        onToggle = onToggle
    )
}

@ExperimentalCoroutinesApi
@Composable
private fun HeartButton(
    modifier: Modifier,
    buttonState: MutableState<HeartAnimationDefinition.HeartButtonState>,
    state: TransitionState,
    onToggle: () -> Unit,
) {
    if(buttonState.value == HeartAnimationDefinition.HeartButtonState.ACTIVE) {
        loadPicture(defaultImage = R.drawable.heart_red).value?.let { image ->
            Image(
                bitmap = image.asImageBitmap(),
                modifier = modifier
                        .clickable(onClick = onToggle, indication = null)
                        .width(state[heartSize])
                        .height(state[heartSize])
            )
        }
    } else {
        loadPicture(defaultImage = R.drawable.heart_grey).value?.let { image ->
            Image(
                bitmap = image.asImageBitmap(),
                modifier = modifier
                        .clickable(onClick = onToggle, indication = null)
                        .width(state[heartSize])
                        .height(state[heartSize])
            )
        }
    }
}

object HeartAnimationDefinition {
    enum class HeartButtonState {
        IDLE, ACTIVE
    }
    val heartColor = ColorPropKey(label = "heartColor")
    val heartSize = DpPropKey(label = "heartDp")

    private val idleIconSize = 50.dp
    private val expandedIconSize = 80.dp
    private val contractedIconSize = 30.dp

    val heartTransitionDefinition = transitionDefinition<HeartButtonState> {
        state(HeartButtonState.IDLE) {
            this[heartColor] = Color.LightGray
            this[heartSize] = idleIconSize
        }
        state(HeartButtonState.ACTIVE) {
            this[heartColor] = Color.Red
            this[heartSize] = idleIconSize
        }
        transition(
            HeartButtonState.IDLE to HeartButtonState.ACTIVE
        ) {
            heartColor using tween(durationMillis = 500)
            heartSize using keyframes {
                durationMillis = 500
                contractedIconSize at 100
                idleIconSize at 200
            }
        }
        transition(
                HeartButtonState.ACTIVE to HeartButtonState.IDLE
        ) {
            heartColor using tween(durationMillis = 500)
            heartSize using keyframes {
                durationMillis = 500
                expandedIconSize at 100
                idleIconSize at 200
            }
        }
    }
}