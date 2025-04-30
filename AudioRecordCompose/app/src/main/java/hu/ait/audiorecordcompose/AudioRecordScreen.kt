package hu.ait.audiorecordcompose

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AudioRecordScreen(
    audioRecordViewModel: AudioRecordViewModel = viewModel(factory = AudioRecordViewModel.factory)
) {
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.RECORD_AUDIO

        )
    )

    Column() {
        if (permissionsState.allPermissionsGranted) {
            Button(onClick = {
                audioRecordViewModel.startRecording()
            }) {
                Text(text = "Record")
            }
            Button(onClick = {
                audioRecordViewModel.stopRecording()
            }) {
                Text(text = "Stop Record")
            }
            Button(onClick = {
                audioRecordViewModel.startPlaying()
            }) {
                Text(text = "Play recording")
            }
            Button(onClick = {
                audioRecordViewModel.stopPlaying()
            }) {
                Text(text = "Stop playing")
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