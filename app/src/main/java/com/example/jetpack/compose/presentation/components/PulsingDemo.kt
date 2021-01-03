package com.example.jetpack.compose.presentation.components

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.animation.transition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.example.jetpack.compose.presentation.components.PulseAnimationDefinitions.pulseDefinition
import com.example.jetpack.compose.presentation.components.PulseAnimationDefinitions.pulsePropKey
import com.example.jetpack.compose.util.TAG

@Composable
fun PulsingDemo() {
    val color = MaterialTheme.colors.primary
    val pulseAnim = transition(
        definition = pulseDefinition,
        initState = PulseAnimationDefinitions.PulseState.INITIAL,
        toState = PulseAnimationDefinitions.PulseState.FINAL
    )

    val pulseMagnitude = pulseAnim[pulsePropKey]
    Log.d(TAG, "PulsingDemo: $pulseMagnitude")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .height(pulseMagnitude.dp)
                .width(pulseMagnitude.dp),
            imageVector = Icons.Default.Favorite
        )
    }
}

object PulseAnimationDefinitions {
    enum class PulseState {
        INITIAL, FINAL
    }

    val pulsePropKey = FloatPropKey("pulseKey")
    val pulseDefinition = transitionDefinition<PulseState> {
        state(PulseState.INITIAL) {
            this[pulsePropKey] = 40f
        }
        state(PulseState.FINAL) {
            this[pulsePropKey] = 50f
        }

        transition(
            PulseState.INITIAL to PulseState.FINAL,
        ) {
            pulsePropKey using infiniteRepeatable(
                animation = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        }
    }
}