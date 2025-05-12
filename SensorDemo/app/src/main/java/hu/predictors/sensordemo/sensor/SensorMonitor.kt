package hu.predictors.sensordemo.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class SensorMonitor @Inject constructor(
    @ApplicationContext private val context: Context
)  {
    private val sensorManager: SensorManager
    private val acceleroSensor: Sensor?

    init {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //acceleroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        acceleroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        //acceleroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    fun startSensorMonitoring() : Flow<SensorEvent> = callbackFlow{
        val callBack = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event != null) {
                    trySend(event)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }

        }

        sensorManager.registerListener(
            callBack,
            acceleroSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        awaitClose { sensorManager.unregisterListener(callBack) }
    }
}

