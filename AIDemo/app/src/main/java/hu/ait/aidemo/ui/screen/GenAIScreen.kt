package hu.ait.aidemo.ui.screen


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GenAIScreen(
    modifier: Modifier = Modifier,
    viewModel: GenAIViewModel = viewModel()
) {

    var textPrompt by rememberSaveable { mutableStateOf("Tell me a joke") }
    val textResult = viewModel.textGenerationResult.collectAsState().value

    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.CAMERA
        )
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
        }
    )


    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        if (permissionsState.allPermissionsGranted) {
            Text("ALL PERMISSIONS GRANTED")
            Button(onClick = {
                //cameraLauncher.launch()
            }) {
                Text("Launch camera")
            }
        } else {
            if (permissionsState.shouldShowRationale) {
                Text("Please please please grant permissions beacause it is needed for the AI..")
            } else {
                Text("No permissions granted yet.")
            }
            Button(
                onClick = {
                    permissionsState.launchMultiplePermissionRequest()
                }
            ) {
                Text("Request permissions")
            }
        }


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = textPrompt,
            onValueChange = {
                textPrompt = it
            }
        )
        Button(onClick = {
            viewModel.generateStory(textPrompt)
        }) {
            Text(text = "Generate")
        }

        Text(text = textResult ?: "No content generated yet",
            fontSize = 30.sp)
    }
}