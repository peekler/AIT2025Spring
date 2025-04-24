package hu.bme.aut.genaidemo.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GenAIScreen(
    viewModel: GenAIViewModel = viewModel(factory = GenAIViewModel.factory),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.RECORD_AUDIO

        ),
        onPermissionsResult = {
            if (it[android.Manifest.permission.RECORD_AUDIO]!!) {
                viewModel.initTTS(context)
            }
        }
    )

    val textResult = viewModel.textGenerationResult.collectAsState().value

    var prompt = rememberSaveable() { mutableStateOf("Tell me a fun fact about IT") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(25.dp)
    ) {

        Text(
            text = "${viewModel.detectedText.value}",
            fontSize = 28.sp,
            fontStyle = FontStyle.Italic
        )
        if (!textResult.isNullOrEmpty()) {
            Button(
                onClick = {
                    viewModel.stopSpeak()
                }
            ) {
                Text("Stop reading")
            }
        }
        Text(
            modifier = Modifier.padding(top = 30.dp),
            fontSize = 28.sp,
            fontFamily = FontFamily.SansSerif,
            text = textResult ?: ""
        )

        if (!textResult.isNullOrEmpty()) {
            Button(
                onClick = {
                    val intentSend = Intent(Intent.ACTION_SEND)
                    intentSend.type = "text/plain"
                    intentSend.putExtra(Intent.EXTRA_TEXT, textResult)
                    context.startActivity(intentSend)
                }
            ) {
                Text("Send")
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (permissionsState.allPermissionsGranted) {
                Button(onClick = {
                    viewModel.startSpeechRecognition()
                }) {
                    Text(text = "Say something")
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
}