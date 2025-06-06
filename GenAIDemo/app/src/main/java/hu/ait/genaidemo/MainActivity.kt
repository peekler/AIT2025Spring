package hu.ait.genaidemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.ait.genaidemo.ui.screen.GenAIScreen
import hu.ait.genaidemo.ui.screen.GenAIViewModel
import hu.ait.genaidemo.ui.screen.GenerativeAIVisionScreen
import hu.ait.genaidemo.ui.theme.GenAIDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GenAIDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GenAIScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
