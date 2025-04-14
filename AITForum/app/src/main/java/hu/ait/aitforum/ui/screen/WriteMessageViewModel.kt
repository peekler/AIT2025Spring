package hu.ait.aitforum.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.aitforum.data.Post

class WriteMessageViewModel : ViewModel() {
    var writePostUiState: WritePostUiState by mutableStateOf(WritePostUiState.Init)

    fun uploadPost(post: Post){
        writePostUiState = WritePostUiState.LoadingPostUpload

        // this represents the "table"
        val postCollection = FirebaseFirestore.getInstance().collection(
            "posts")
        postCollection.add(post)
            .addOnSuccessListener {
                writePostUiState = WritePostUiState.PostUploadSuccess
            }
            .addOnFailureListener{
                writePostUiState = WritePostUiState.ErrorDuringPostUpload(
                    it.localizedMessage)
            }
    }
}

sealed interface WritePostUiState {
    object Init : WritePostUiState
    object LoadingPostUpload : WritePostUiState
    object PostUploadSuccess : WritePostUiState
    data class ErrorDuringPostUpload(val error: String?) : WritePostUiState
}