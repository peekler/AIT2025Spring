package hu.ait.todocomposedemo

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
import hu.ait.todocomposedemo.ui.navigation.SummaryScreenRoute
import hu.ait.todocomposedemo.ui.navigation.TodoScreenRoute
import hu.ait.todocomposedemo.ui.screen.SummaryScreen
import hu.ait.todocomposedemo.ui.screen.TodoScreen
import hu.ait.todocomposedemo.ui.theme.TodoComposeDemoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoComposeDemoTheme {
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
    navController: NavHostController = rememberNavController()
)
{
    NavHost(
        //modifier = modifier,
        navController = navController,
        startDestination = TodoScreenRoute
    )
    {
        composable<TodoScreenRoute> {
            TodoScreen(
                onInfoClicked = {
                    allTodo, importantTodo ->
                    navController.navigate(SummaryScreenRoute(
                        allTodo,importantTodo
                    ))
                }
            )
        }
        composable<SummaryScreenRoute> {
            SummaryScreen()
        }
    }
}