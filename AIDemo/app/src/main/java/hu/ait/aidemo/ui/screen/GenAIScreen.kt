package hu.ait.aidemo.ui.screen


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import hu.ait.aidemo.ComposeFileProvider
import coil.compose.AsyncImage


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

    var hasImage by remember {
        mutableStateOf(false)
    }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
        }
    )


    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        if (permissionsState.allPermissionsGranted) {
            Text("ALL PERMISSIONS GRANTED")
            Button(onClick = {
                val uri = ComposeFileProvider.getImageUri(context)
                imageUri = uri
                cameraLauncher.launch(uri)
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

        if (hasImage && imageUri != null) {
            AsyncImage(
                model = imageUri,
                modifier = Modifier.size(200.dp, 200.dp),
                contentDescription = "Selected image"
            )
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

        Button(onClick = {
            viewModel.generateContentWithPromptAndImage(
                textPrompt,
                getBitmapFromUri(context, imageUri!!)!!
            )
        }) {
            Text(text = "Generate with Image")
        }

        Text(text = textResult ?: "No content generated yet",
            fontSize = 30.sp)
    }
}

fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
