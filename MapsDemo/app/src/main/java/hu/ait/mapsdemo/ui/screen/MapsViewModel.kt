package hu.ait.mapsdemo.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng


class MapsViewModel: ViewModel() {

    // --- Maps related
    private var _markerPositionList =
        mutableStateListOf<LatLng>()

    fun getMarkersList(): List<LatLng> {
        return _markerPositionList
    }

    fun addMarkerPosition(latLng: LatLng) {
        _markerPositionList.add(latLng)
    }
}
