package hu.ait.highlowgame.ui.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController


@Composable
fun MainScreen(modifier: Modifier = Modifier,
               onStartAction: () -> Unit,
               onAboutAction: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(0.6f),
            onClick = {
                onStartAction()
            }) {
            Text("Start")
        }
        Button(
            modifier = Modifier.fillMaxWidth(0.6f),
            onClick = {
                onAboutAction()
            }) {
            Text("About")
        }
        Button(
            modifier = Modifier.fillMaxWidth(0.6f),
            onClick = {}) {
            Text("Help")
        }
    }
}