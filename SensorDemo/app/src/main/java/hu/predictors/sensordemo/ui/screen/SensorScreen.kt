package hu.predictors.sensordemo.ui.screen

import android.hardware.SensorEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SensorScreen(
    sensorViewModel: SensorViewModel = hiltViewModel()
) {
    val sensorState by sensorViewModel.sensorFlow.collectAsState()


    LaunchedEffect(key1 = Unit) {
        sensorViewModel.startSensorMonitoring()
    }

    Column() {
        Text(text = "Sensor value: ${getSensorTextValue(sensorState)}",
            fontSize = 28.sp)
    }
}

fun getSensorTextValue(values: FloatArray?) : String {
    if (values != null) {
        return "${values[0].toDouble()}"
        /*val accX = values[0].toDouble()
        val accY = values[1].toDouble()
        val accZ = values[2].toDouble()
        return """

                X: $accX
                Y: $accY
                Z: $accZ
            """.trimIndent()*/
    }
    else {
        return ""
    }
}