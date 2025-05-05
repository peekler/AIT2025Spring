package hu.ait.texttospeechspeechrecognition

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TTSScreen(
    ttsViewModel: TTSViewModel = viewModel(factory = TTSViewModel.factory)
) {
    val context = LocalContext.current

    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.RECORD_AUDIO

        ),
        onPermissionsResult = {
            if (it[android.Manifest.permission.RECORD_AUDIO]!!) {
                ttsViewModel.initTTS(context)
            }
        }
    )
    var textToSpeech by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (permissionsState.allPermissionsGranted) {
            Button(onClick = {
                ttsViewModel.startSpeechRecognition()
            }) {
                Text(text = "Detect speech")
            }
            Text(
                text = "${ttsViewModel.detectedText.value}",
                fontSize = 26.sp
            )
            TextField(value = textToSpeech, onValueChange = {
                textToSpeech = it
            })
            Button(onClick = {
                ttsViewModel.speekLoud(textToSpeech)
            }) {
                Text(text = "Read text")
            }
        } else {
            Button(onClick = {
                permissionsState.launchMultiplePermissionRequest()
            }) {
                Text(text = "Request permissions")
            }
        }
    }

}