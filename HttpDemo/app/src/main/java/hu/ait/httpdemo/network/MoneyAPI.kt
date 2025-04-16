package hu.ait.httpdemo.network

import hu.ait.httpdemo.data.MoneyResult
import retrofit2.http.GET
import retrofit2.http.Query

// http://data.fixer.io/api/latest?access_key=969c37b5a73f8cb2d12c081dcbdc35e6

//Host: http://data.fixer.io
//Path: api/latest
//Query params: access_key=969c37b5a73f8cb2d12c081dcbdc35e6

interface MoneyAPI {

    @GET("api/latest")
    suspend fun getRates(@Query("access_key") accessKey: String): MoneyResult
}