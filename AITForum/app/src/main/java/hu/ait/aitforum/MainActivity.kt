package hu.ait.aitforum

import android.os.Bundle
import android.util.Log
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
import hu.ait.aitforum.navigation.LoginScreenRoutes
import hu.ait.aitforum.navigation.MessagesScreenRoutes
import hu.ait.aitforum.navigation.WriteMessageScreenRoutes
import hu.ait.aitforum.ui.screen.LoginScreen
import hu.ait.aitforum.ui.screen.MessagesScreen
import hu.ait.aitforum.ui.screen.WriteMessageScreen
import hu.ait.aitforum.ui.theme.AITForumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AITForumTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainNavigation(modifier = Modifier.padding(innerPadding))
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
        startDestination = LoginScreenRoutes
    )
    {
        composable<LoginScreenRoutes> {
            LoginScreen(onLoginSuccess = {navController.navigate(MessagesScreenRoutes)})
        }
        composable<MessagesScreenRoutes> {
            MessagesScreen(
                onNewMessageClick = {
                    navController.navigate(WriteMessageScreenRoutes)
                }
            )
        }
        composable<WriteMessageScreenRoutes> {
            WriteMessageScreen()
        }
    }
}


