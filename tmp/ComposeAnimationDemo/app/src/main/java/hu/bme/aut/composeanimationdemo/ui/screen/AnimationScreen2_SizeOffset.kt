package hu.bme.aut.composeanimationdemo.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun AnimationScreen2() {
    var sizeState by remember { mutableStateOf(200.dp) }

    val size by animateDpAsState(
        targetValue = sizeState,
        keyframes {
            durationMillis = 5000
            sizeState * 0.8f at 1000 with LinearEasing
            sizeState * 2f at 3000 with LinearEasing
            sizeState * 0.1f at 4000 with FastOutLinearInEasing
            sizeState * 1f at 5000 with FastOutLinearInEasing
        }, label = "size"
    )

    var moved by remember { mutableStateOf(false) }
    val pxToMove = with(LocalDensity.current) {
        100.dp.toPx().roundToInt() }

    val offset by animateIntOffsetAsState(
        targetValue = if (moved) {
            IntOffset(pxToMove, pxToMove)
        } else { IntOffset.Zero }
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = { sizeState += 50.dp }) {
            Text(text = "Increase")
        }
        Box(
            modifier = Modifier.size(size).background(Color.Green)
        )

        Button(
            onClick = {moved = !moved},
            modifier = Modifier
                .offset {
                    offset
                }.indication(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null)
        ) { Text(text = "Offset animation demo") }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieHead(name: String, year: String, imageId: Int) {
    Column {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            modifier = Modifier
                .padding(5.dp)
                .wrapContentHeight()
        ) {
            var expanded by rememberSaveable { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(20.dp)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioHighBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
            ) {
                Row() {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded)
                                Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            contentDescription = if (expanded) {
                                "Less"
                            } else {
                                "More"
                            }
                        )
                    }
                    Image(
                        painter = painterResource(id = imageId),
                        contentDescription = "Movie"
                    )
                }

                if (expanded) {
                    Text(text = "$name")
                    Text(text = "$year")
                }
            }
        }
    }

}