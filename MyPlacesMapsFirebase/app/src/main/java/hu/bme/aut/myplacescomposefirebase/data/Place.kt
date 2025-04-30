package hu.bme.aut.myplacescomposefirebase.data

import com.google.android.gms.maps.model.LatLng

data class Place(
    var uid: String = "",
    var author: String = "",
    var placeTitle: String = "",
    var placeText: String = "",
    var lat: Double = 0.0,
    var lng: Double = 0.0
)

data class PlaceWithId(
    val placeId: String,
    val place: Place
)