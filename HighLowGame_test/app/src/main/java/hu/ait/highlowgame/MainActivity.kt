package hu.ait.highlowgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.ait.highlowgame.ui.screen.about.AboutScreen
import hu.ait.highlowgame.ui.screen.game.GameScreen
import hu.ait.highlowgame.ui.screen.main.MainScreen
import hu.ait.highlowgame.ui.theme.HighLowGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HighLowGameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    innerPadding ->

                    MainNavigation(
                        modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "mainscreen"
){
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("mainscreen") {
            MainScreen(onStartAction = {
                navController.navigate("gamescreen?maxnum=100")
            }, onAboutAction = {
                navController.navigate("aboutscreen")
            })
        }
        composable("gamescreen?maxnum={maxnum}") {
            GameScreen()
        }

        composable("aboutscreen") { AboutScreen() }
    }
}
