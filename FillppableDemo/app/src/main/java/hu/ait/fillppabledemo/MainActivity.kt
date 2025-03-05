package hu.ait.fillppabledemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.wajahatkarim.flippable.Flippable
import com.wajahatkarim.flippable.rememberFlipController
import hu.ait.fillppabledemo.ui.theme.FillppableDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FillppableDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        val flipController = rememberFlipController()

        Flippable(
            frontSide = { Text("FRONT", fontSize = 64.sp) },
            backSide = {Text("BACK", fontSize = 64.sp) },
            flipController,
            flipOnTouch = false
        )

        Button(onClick = {
            flipController.flip()
        }) {
            Text("Flip")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FillppableDemoTheme {
        Greeting("Android")
    }
}