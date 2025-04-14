package hu.ait.aitforum.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import hu.ait.aitforum.data.Post
import java.util.Date

@Composable
fun WriteMessageScreen(
    viewModel: WriteMessageViewModel = viewModel()
) {
    var postTitle by remember { mutableStateOf("") }
    var postBody by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        OutlinedTextField(
            value = postTitle,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Post title") },
            onValueChange = {
                postTitle = it
            }
        )
        OutlinedTextField(
            value = postBody,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Post body") },
            onValueChange = {
                postBody = it
            }
        )

        OutlinedButton(
            onClick = {
                viewModel.uploadPost(
                    Post(
                        Firebase.auth.currentUser!!.uid,
                        Firebase.auth.currentUser!!.email!!,
                        Date(System.currentTimeMillis()).toString(),
                        postTitle,
                        postBody,
                    )
                )
            }
        ) {
            Text("Send")
        }

        when (viewModel.writePostUiState) {
            is WritePostUiState.Init -> {}
            is WritePostUiState.LoadingPostUpload -> CircularProgressIndicator()
            is WritePostUiState.PostUploadSuccess -> {
                Text("Post uploaded")
            }
            is WritePostUiState.ErrorDuringPostUpload -> {
                Text("Error: ${
                    (viewModel.writePostUiState as WritePostUiState.ErrorDuringPostUpload)
                        .error}")
            }
        }
    }
}