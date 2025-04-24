package hu.ait.genaidemo.ui.screen

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.type.content


class GenAIVisionViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<VisionUIState> =
        MutableStateFlow(VisionUIState.Initial)
    val uiState: StateFlow<VisionUIState> =
        _uiState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = "YOUR_KEY_HERE"
    )

    fun sendPrompt(
        bitmap: Bitmap,
        prompt: String
    ) {
        _uiState.value = VisionUIState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(
                    content {
                        image(bitmap)
                        text(prompt)
                    }
                )
                response.text?.let { outputContent ->
                    _uiState.value = VisionUIState.Success(outputContent)
                }
            } catch (e: Exception) {
                _uiState.value = VisionUIState.Error(e.localizedMessage ?: "")
            }
        }
    }
}


sealed interface VisionUIState {
    object Initial : VisionUIState
    object Loading : VisionUIState
    data class Success(val outputText: String) : VisionUIState
    data class Error(val errorMessage: String) : VisionUIState
}