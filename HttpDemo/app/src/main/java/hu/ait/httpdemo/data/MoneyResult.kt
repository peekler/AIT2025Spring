package hu.ait.httpdemo.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoneyResult(
    @SerialName("base")
    var base: String? = null,
    @SerialName("date")
    var date: String? = null,
    @SerialName("rates")
    var rates: Rates? = null,
    @SerialName("success")
    var success: Boolean? = null,
    @SerialName("timestamp")
    var timestamp: Int? = null
)