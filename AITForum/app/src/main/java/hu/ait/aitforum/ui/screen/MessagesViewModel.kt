package hu.ait.aitforum.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import hu.ait.aitforum.data.Post
import hu.ait.aitforum.data.PostWithId
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class MessagesViewModel : ViewModel() {
    //var messagesUIState: MessagesUIState by mutableStateOf(MessagesUIState.Init)

    fun postsList() = callbackFlow {
        val snapshotListener = Firebase.firestore.collection("posts")
            .orderBy("postDate", Query.Direction.DESCENDING)
            .addSnapshotListener() { snapshot, e ->
                val response = if (snapshot != null) {
                    val postList = snapshot.toObjects(Post::class.java)
                    val postWithIdList = mutableListOf<PostWithId>()

                    postList.forEachIndexed { index, post ->
                        postWithIdList.add(PostWithId(snapshot.documents[index].id, post))
                    }

                    MessagesUIState.Success(postWithIdList)

                } else {
                    MessagesUIState.Error(e?.localizedMessage)
                }

                trySend(response)
            }
        awaitClose {
            // when the viewmodel destroys,
            // the flow stops and here we can stop monitoring the database
            snapshotListener.remove()
        }
    }

    fun deletePost(postId: String) {
        FirebaseFirestore.getInstance().collection("posts")
            .document(postId).delete()
    }

}

sealed interface MessagesUIState {
    object Init : MessagesUIState
    object Loading : MessagesUIState
    data class Success(val postList: List<PostWithId>) : MessagesUIState
    data class Error(val error: String?) : MessagesUIState
}