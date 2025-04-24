package hu.ait.httpdemo

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.ait.httpdemo.navigation.MainScreenRoute
import hu.ait.httpdemo.navigation.MoneyScreenRoute
import hu.ait.httpdemo.navigation.NasaScreenRoute
import hu.ait.httpdemo.ui.screen.MainScreen
import hu.ait.httpdemo.ui.screen.money.MoneyScreen
import hu.ait.httpdemo.ui.screen.nasa.NasaMarsScreen
import hu.ait.httpdemo.ui.theme.HttpDemoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HttpDemoTheme {
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
        startDestination = MainScreenRoute
    )
    {
        composable<MainScreenRoute> {
            MainScreen(
                onMoneyAPISelected = {
                    navController.navigate(MoneyScreenRoute)
                },
                onNasaMarsAPISelected = {
                    navController.navigate(NasaScreenRoute)
                }
            )
        }
        composable<MoneyScreenRoute> {
            MoneyScreen()
        }
        composable<NasaScreenRoute> {
            NasaMarsScreen()
        }
    }
}