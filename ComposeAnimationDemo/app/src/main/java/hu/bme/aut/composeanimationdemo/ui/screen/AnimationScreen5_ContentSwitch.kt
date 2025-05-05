package hu.bme.aut.composeanimationdemo.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

enum class UiState { Loading, Loaded, Error }

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimationScreen5() {
    var state by remember {
        mutableStateOf(UiState.Loading)
    }

    Column {
        AnimatedContent(
            state,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(3000)
                ) with fadeOut(animationSpec = tween(3000))
            },
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                state = when (state) {
                    UiState.Loading -> UiState.Loaded
                    UiState.Loaded -> UiState.Error
                    UiState.Error -> UiState.Loading
                }
            },
            label = "Animated Content"
        ) { targetState ->
            when (targetState) {
                UiState.Loading -> {
                    CircularProgressIndicator()
                }

                UiState.Loaded -> {
                    Text(text = "Completed")
                }

                UiState.Error -> {
                    Text(text = "Error")
                }
            }
        }
    }
}