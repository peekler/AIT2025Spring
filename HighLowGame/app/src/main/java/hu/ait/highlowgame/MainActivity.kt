package hu.ait.highlowgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.ait.highlowgame.ui.navigation.GameScreen
import hu.ait.highlowgame.ui.navigation.MainMenuScreen
import hu.ait.highlowgame.ui.screen.GameScreen
import hu.ait.highlowgame.ui.screen.MainMenuScreen
import hu.ait.highlowgame.ui.theme.HighLowGameTheme
import kotlinx.coroutines.MainScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HighLowGameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainNavigation(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainMenuScreen
    )
    {
        composable<MainMenuScreen> {
            MainMenuScreen(
                onStartClick = {
                    navController.navigate(GameScreen(100))
                }
            )
        }
        composable<GameScreen> {
            GameScreen()
        }
    }
}
