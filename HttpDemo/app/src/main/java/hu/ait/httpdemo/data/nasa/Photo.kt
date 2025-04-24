package hu.ait.httpdemo.data.nasa


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    @SerialName("camera")
    var camera: Camera? = null,
    @SerialName("earth_date")
    var earthDate: String? = null,
    @SerialName("id")
    var id: Int? = null,
    @SerialName("img_src")
    var imgSrc: String? = null,
    @SerialName("rover")
    var rover: Rover? = null,
    @SerialName("sol")
    var sol: Int? = null
)