package hu.bme.aut.myplacescomposefirebase.ui.screen.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import hu.bme.aut.myplacescomposefirebase.data.Place
import hu.bme.aut.myplacescomposefirebase.data.PlaceWithId
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

sealed interface MainUiState {
    object Init : MainUiState
    object UploadPlaceInProgress : MainUiState
    object PlaceUploadSuccess : MainUiState
    data class PlacesRetrieved(val placeList: List<PlaceWithId>) : MainUiState
    data class Error(val error: String?) : MainUiState
}

class MainViewModel: ViewModel(){
    companion object {
        const val COLLECTION_PLACES = "places"
    }

    var mainUiState: MainUiState by mutableStateOf(MainUiState.Init)
    private var auth: FirebaseAuth
    var currentUserId: String

    init {
        auth = Firebase.auth
        currentUserId = auth.currentUser!!.uid
    }

    fun uploadPlace(latLng: LatLng, placeTitle: String, placeText: String) {
        mainUiState = MainUiState.UploadPlaceInProgress

        val newPlace = Place(
            uid = auth.currentUser!!.uid,
            author = auth.currentUser!!.email!!,
            placeTitle = placeTitle,
            placeText = placeText,
            lat = latLng.latitude,
            lng = latLng.longitude
        )

        val placeCollection = FirebaseFirestore.getInstance().collection(COLLECTION_PLACES)
        placeCollection.add(newPlace).addOnSuccessListener {
            mainUiState = MainUiState.PlaceUploadSuccess
        }.addOnFailureListener{
            mainUiState = MainUiState.Error(it.message)
        }

    }

    fun placeList() = callbackFlow {
        val snapshotListener =
            FirebaseFirestore.getInstance().collection(COLLECTION_PLACES)
                .addSnapshotListener() { snapshot, e ->
                    val response = if (snapshot != null) {

                        val placeList = snapshot.toObjects(Place::class.java)
                        val placeWithIdList = mutableListOf<PlaceWithId>()

                        placeList.forEachIndexed { index, place ->
                            placeWithIdList.add(PlaceWithId(snapshot.documents[index].id,
                                place))
                        }

                        MainUiState.PlacesRetrieved(
                            placeWithIdList
                        )
                    } else {
                        MainUiState.Error(e?.message.toString())
                    }

                    trySend(response) // emit this value through the flow
                }
        awaitClose {
            snapshotListener.remove()
        }
    }

    fun deletePlace(placeKey: String) {
        FirebaseFirestore.getInstance().collection(
            COLLECTION_PLACES
        ).document(placeKey).delete()
    }

}