package hu.ait.composebasiclayoutdemos
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.ait.composebasiclayoutdemos.ui.theme.ComposeBasicLayoutDemosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBasicLayoutDemosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //RowDemo()
                    //ColumnWeightDemo()
                    //VerticalDemo()
                    //ScrollColumnDemo()
                    //BoxDemo()
                    Movie()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Movie() {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable(onClick = {})
    ) {

        Spacer(Modifier.size(10.dp))
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
                pressedElevation = 2.dp,
                focusedElevation = 4.dp
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.running),
                contentDescription = "Forrest Gump",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}


@Composable
private fun ScrollColumnDemo() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        repeat(100) {
            Text("Item $it", modifier = Modifier.padding(2.dp))
        }
    }
}


@Composable
fun VerticalDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .border(width = 1.dp, color = Color.Blue),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End
    ) {
        Text(text = "Forrest Gump")
        Text(text = "1994")
        Text(text = "Movie")
    }
}

@Composable
fun ColumnWeightDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .border(width = 1.dp, color = Color.Blue),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "Forrest Gump",
            modifier = Modifier
                .weight(10.0f)
                .fillMaxWidth()
                .background(Color.Blue))
        Text(text = "1994",
            modifier = Modifier
                .weight(10.0f)
                .fillMaxWidth()
                .background(Color.Yellow))
        Text(text = "Movie",
            modifier = Modifier
                .weight(10.0f)
                .fillMaxWidth()
                .background(Color.Green))
    }
}


@Composable
fun RowDemo() {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.running),
            contentDescription = "Forrest Gump",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
        )
        Column {
            Text(text = "Forrest Gump")
            Text(text = "1994")
        }
    }
}

@Composable
fun BoxDemo() {
    Box(
        modifier = Modifier
            .size(400.dp)
            .border(width = 2.dp, color = Color.Magenta)
    ) {
        Image(
            painter = painterResource(id = R.drawable.running),
            contentDescription = "Forrest Gump",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        )

        Text(
            text = "TopStart",
            modifier = Modifier.align(Alignment.TopStart)
        )
        Text(
            text = "TopCenter",
            modifier = Modifier.align(Alignment.TopCenter)
        )
        Text(
            text = "TopEnd",
            modifier = Modifier.align(Alignment.TopEnd)
        )

        Text(
            text = "CenterStart",
            modifier = Modifier.align(Alignment.CenterStart)
        )
        Text(
            text = "CenterEnd",
            modifier = Modifier.align(Alignment.CenterEnd)
        )
        Text(
            text = "Center",
            modifier = Modifier.align(Alignment.Center)
        )

        Text(
            text = "BottomStart",
            modifier = Modifier.align(Alignment.BottomStart)
        )
        Text(
            text = "BottomEnd",
            modifier = Modifier.align(Alignment.BottomEnd)
        )
        Text(
            text = "BottomCenter",
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}