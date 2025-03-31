package hu.ait.sideeffetdemo.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun SideEffectScreen() {

    var counter by remember { mutableStateOf(0) }

    //LaunchedEffect(key1 = counter) {
    LaunchedEffect(key1 = Unit) {
        Log.d("TAG_SIDE", "LaunchedEffect CALLED")
    }

    // called every time during recomposition
    SideEffect {
        Log.d("TAG_SIDE", "SIDEFFECT CALLED")
    }

    DisposableEffect(key1 = Unit) {
        Log.d("TAG_SIDE", "LAUNCH in DisposeEffect CALLED")

        onDispose {
            Log.d("TAG_SIDE", "DisposableEffect CALLED")
        }
    }


    Column {
        Text("SIDEEFFECT Demo $counter")
        Button(onClick = {
            counter++
        }) {
            Text("Increase - state change")
        }
    }

}