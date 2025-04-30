package hu.ait.mapsdemo.location

import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class LocationManager @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) {
    @SuppressLint("MissingPermission")
    fun fetchUpdates(): Flow<Location> = callbackFlow {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 1000).apply {
            setMinUpdateDistanceMeters(5f)
            setGranularity(Granularity.GRANULARITY_FINE)
            //setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            //setWaitForAccurateLocation(true)
        }.build()

        val callBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val location = locationResult.lastLocation
                if (location != null) {
                    trySend(location) // this is when the flow sends a new Location out...
                }
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, callBack, Looper.getMainLooper())

        // Suspends the current coroutine until the channel is either
        // closed or cancelled and invokes the given block before resuming the coroutine.
        awaitClose {
            fusedLocationProviderClient.removeLocationUpdates(callBack) }
    }
}