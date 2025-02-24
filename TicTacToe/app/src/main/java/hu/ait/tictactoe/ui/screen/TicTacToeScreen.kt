package hu.ait.tictactoe.ui.screen

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.sp

@Composable
fun TicTacToeScreen(modifier: Modifier = Modifier) {
    var circleX by remember { mutableStateOf(0f) }
    var circleY by remember { mutableStateOf(0f) }

    var points by remember { mutableStateOf<List<Offset>>(emptyList()) }

    Column(modifier = modifier) {
        Text("Tic Tac Toe", fontSize = 30.sp)

        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(key1 = Unit) {
                
                // it = where I have clicked and the motionevent..
                detectTapGestures {
                    offset ->
                        Log.d("TAG", "TicTacToeScreen: ${offset.x}, ${offset.y} ")
                        circleX = offset.x
                        circleY = offset.y

                        points = points + offset
                }
            }

        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawRect(
                color = Color.Blue,
                size = Size(canvasWidth, canvasHeight)
            )

            drawLine(
                color = Color.Red,
                start = Offset(0f, 0f),
                end = Offset(canvasWidth, canvasHeight),
                strokeWidth = 10f
            )

            /*drawCircle(
                color = Color.Red,
                center = Offset(circleX, circleY),
                radius = 50f,
                style = Fill
            )*/

            points.forEach {
                drawCircle(
                    color = Color.Red,
                    center = it, // it is the current coordinate in the points array
                    radius = 50f,
                    style = Fill
                )
            }
        }
    }
}