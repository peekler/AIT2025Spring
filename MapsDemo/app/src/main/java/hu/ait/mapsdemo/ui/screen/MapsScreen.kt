package hu.ait.mapsdemo.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MapsScreen(modifier: Modifier,
               mapsViewModel: MapsViewModel = viewModel()
               )
{

    var cameraState = rememberCameraPositionState {
        CameraPosition.fromLatLngZoom(
            LatLng(47.0, 19.0), 10f
        )
    }
    var uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = true,
                zoomGesturesEnabled = true
            )
        )
    }
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.SATELLITE,
                isTrafficEnabled = true,
            )
        )
    }



    Column(modifier = modifier.fillMaxSize()) {
        var isSatellite by remember { mutableStateOf(true) }
        Switch(
            checked = isSatellite,
            onCheckedChange = {
                isSatellite = it
                mapProperties = mapProperties.copy(
                    mapType = if (it) MapType.SATELLITE else MapType.NORMAL)
            }
        )


        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            cameraPositionState = cameraState,
            uiSettings = uiSettings,
            properties = mapProperties,
            onMapClick = {
                clickCoordinate ->
                    mapsViewModel.addMarkerPosition(clickCoordinate)
            }
        ) {
            Marker(
                state = MarkerState(position = LatLng(47.0, 19.0)),
                title = "Marker AIT",
                snippet = "Marker long text, lorem ipsum...",
                draggable = true,
                alpha = 0.5f
            )

            for (markerPosition in mapsViewModel.getMarkersList()) {
                Marker(
                    state = MarkerState(position = markerPosition),
                    title = "Marker",
                    snippet = "Coord: " +
                            "${markerPosition.latitude}," +
                            "${markerPosition.longitude}"
                )
            }

        }
    }

}