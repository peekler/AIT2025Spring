package hu.ait.httpdemo.data.nasa


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoverPhotos(
    @SerialName("photos")
    var photos: List<Photo?>? = null
)