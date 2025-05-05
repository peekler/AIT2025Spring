package hu.bme.aut.composeanimationdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.bme.aut.composeanimationdemo.ui.screen.AnimationScreen1
import hu.bme.aut.composeanimationdemo.ui.screen.AnimationScreen2
import hu.bme.aut.composeanimationdemo.ui.screen.AnimationScreen3
import hu.bme.aut.composeanimationdemo.ui.screen.AnimationScreen4
import hu.bme.aut.composeanimationdemo.ui.screen.AnimationScreen5
import hu.bme.aut.composeanimationdemo.ui.screen.MovieHead
import hu.bme.aut.composeanimationdemo.ui.theme.ComposeAnimationDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAnimationDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //AnimationScreen1() // visibility, background color
                    //MovieHead(name = "Forrest Gump", year = "1994", imageId = R.drawable.movie) // expand: size change
                    //AnimationScreen2() // size change, x-y offset
                    //AnimationScreen3() // padding and elevation animations
                    //AnimationScreen4() // text scale and color
                    AnimationScreen5() // Animated content
                }
            }
        }
    }
}
