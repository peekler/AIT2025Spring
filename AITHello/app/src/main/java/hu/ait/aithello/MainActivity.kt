package hu.ait.aithello

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import hu.ait.aithello.ui.theme.AITHelloTheme
import java.util.Date

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AITHelloTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "AIT MOBILE",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var dateText by remember { mutableStateOf("") }

    Column {
        Text(
            text = "DATE: $dateText",
            fontSize = 32.sp,
            modifier = modifier
        )
        Button(
            onClick = {
                dateText =
                    Date(System.currentTimeMillis()).toString()
            }
        ) {
            Text(text = "Show time")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AITHelloTheme {
        Greeting("Android")
    }
}