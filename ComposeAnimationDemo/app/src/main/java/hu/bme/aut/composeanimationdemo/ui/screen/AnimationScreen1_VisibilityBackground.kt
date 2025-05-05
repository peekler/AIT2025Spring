package hu.bme.aut.composeanimationdemo.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun AnimationScreen1() {
    var isVisible by remember {
        mutableStateOf(false)
    }

    val animatedAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1.0f else 0f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )

    val animatedColor by animateColorAsState(
        if (isVisible) Color.Green else Color.Blue,
        animationSpec = tween(durationMillis = 3000)
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Switch(
            modifier = Modifier.semantics { contentDescription = "Visibility" },
            checked = isVisible,
            onCheckedChange = { isVisible = it }
        )

        Column(
            modifier = Modifier.drawBehind {
                drawRect(animatedColor)
            }.fillMaxWidth().height(200.dp)
        ) {
            // your composable here
        }

        Box(
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    alpha = animatedAlpha
                }
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Green)
        ) {
        }

        AnimatedVisibility(visible = isVisible,
            modifier = Modifier.fillMaxWidth().weight(1f),
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
            ) {

            Box(
                modifier = Modifier.background(Color.Red)
            )

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Button")
            }
        }
    }
}