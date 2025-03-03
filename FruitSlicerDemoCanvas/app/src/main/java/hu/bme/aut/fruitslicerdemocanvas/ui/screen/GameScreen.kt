package hu.bme.aut.fruitslicerdemocanvas.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(
    gameViewModel: GameViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        gameViewModel.initMedia(context = context)
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            gameViewModel.stopGame()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .onSizeChanged {
                    gameViewModel.startGame(it.width - 40, it.height - 40)
                }
                .pointerInput(key1 = Unit) {
                    detectDragGestures(
                        onDragStart = { touch ->
                            gameViewModel.points = listOf(touch)
                            gameViewModel.playSword()
                        },
                        onDrag = { change, dragAmount ->
                            val newPoint = change.position
                            gameViewModel.points = gameViewModel.points + newPoint
                        },
                        onDragEnd = {
                            if (gameViewModel.splashFruits()) {
                                gameViewModel.playSplash()
                            }
                        }
                    )
                }
        ) {
            // access DrawScope here
            drawRect(
                color = Color.Blue,
                size = size
            )

            gameViewModel.fruitPoints.forEach {
                drawCircle(brush = Brush.horizontalGradient(
                    colors = listOf(Color.Green, Color.Yellow),
                    //), radius =  GameViewModel.FRUIT_RADIUS, style= Stroke(width = 8.dp.toPx()), center = it)
                ), radius =  GameViewModel.FRUIT_RADIUS, style= Fill, center = it)
            }

            if (gameViewModel.points.size > 1) {
                val path = Path().apply {
                    val firstPoint = gameViewModel.points.first()
                    val rest = gameViewModel.points.subList(1, gameViewModel.points.size - 1)

                    moveTo(firstPoint.x, firstPoint.y)
                    rest.forEach {
                        lineTo(it.x, it.y)
                    }
                }

                drawPath(path, Color.Red, style = Stroke(width = 5.dp.toPx()))
            }
        }
    }


}