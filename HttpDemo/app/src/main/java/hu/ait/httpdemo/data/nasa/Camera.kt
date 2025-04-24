package hu.ait.httpdemo.data.nasa


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Camera(
    @SerialName("full_name")
    var fullName: String? = null,
    @SerialName("id")
    var id: Int? = null,
    @SerialName("name")
    var name: String? = null,
    @SerialName("rover_id")
    var roverId: Int? = null
)