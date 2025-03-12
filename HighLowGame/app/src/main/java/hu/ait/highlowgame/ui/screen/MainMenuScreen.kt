package hu.ait.highlowgame.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun MainMenuScreen(
    onStartClick: ()-> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = {
            onStartClick()
        }) {
            Text("Start")
        }
        Button(onClick = {
            //
        }) {
            Text("Help")
        }
        Button(onClick = {
            //
        }) {
            Text("About")
        }
    }
}