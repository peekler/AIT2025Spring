package hu.ait.httpdemo.data.nasa


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rover(
    @SerialName("id")
    var id: Int? = null,
    @SerialName("landing_date")
    var landingDate: String? = null,
    @SerialName("launch_date")
    var launchDate: String? = null,
    @SerialName("name")
    var name: String? = null,
    @SerialName("status")
    var status: String? = null
)