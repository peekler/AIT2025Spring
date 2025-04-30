package hu.ait.aidemo.ui.screen

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.ai.client.generativeai.type.content


class GenAIViewModel : ViewModel() {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = "AIzaSyDq6ppwq4Kk8HlNuV2QDcOfK3sKxsGJpLA",

    )

    private val _textGenerationResult = MutableStateFlow<String?>(null)
    val textGenerationResult = _textGenerationResult.asStateFlow()

    fun generateStory(userPrompt: String = "Tell me a joke") {
        _textGenerationResult.value = "Generating..."
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var systemPrompt = "You are a helpful assistant. Who answers always in 1 sentence."

                var finalPrompt = "$systemPrompt" +
                        "Return only one answer, do not generate any other description or text." +
                        "$userPrompt"

                val result = generativeModel.generateContent(
                    finalPrompt
                )

                _textGenerationResult.value = result.text
            } catch (e: Exception) {
                _textGenerationResult.value = "Error: ${e.message}"
            }
        }
    }


    fun generateContentWithPromptAndImage(
        userPrompt: String = "Tell me a joke",
        bitmap: Bitmap
    ) {
        _textGenerationResult.value = "Generating..."
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = generativeModel.generateContent(
                    content {
                        image(bitmap)
                        text(userPrompt)
                    }
                )


                _textGenerationResult.value = result.text
            } catch (e: Exception) {
                _textGenerationResult.value = "Error: ${e.message}"
            }
        }
    }


}