package hu.predictors.sensordemo.ui.screen

import android.hardware.SensorEvent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.predictors.sensordemo.sensor.SensorMonitor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SensorViewModel @Inject constructor(
    private val sensorMonitor: SensorMonitor
) : ViewModel() {
    private val _sensorFlow = MutableStateFlow<FloatArray?>(null)
    val sensorFlow = _sensorFlow.asStateFlow()

    fun startSensorMonitoring() {
        viewModelScope.launch {
            sensorMonitor
                .startSensorMonitoring()
                .collect {
                    _sensorFlow.emit(it.values.clone())
                }
        }
    }
}