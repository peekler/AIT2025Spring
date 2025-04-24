package hu.bme.aut.genaidemo.ui.screen

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class GenAIViewModel(private var context: Context) : ViewModel(), TextToSpeech.OnInitListener {

    val TAG = "TAG_TTS_SPEECH"

    private lateinit var speechRecognizer: android.speech.SpeechRecognizer

    private lateinit var textToSpeech: TextToSpeech

    var detectedText = mutableStateOf("")

    init {
        initTTS(context)
    }

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = "YOUR_KEY_HERE"
    )

    private val _textGenerationResult = MutableStateFlow<String?>(null)
    val textGenerationResult = _textGenerationResult.asStateFlow()

    fun generateAnswer(prompt: String) {
        _textGenerationResult.value = "Generating..."
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // This is the real stuff...
                val result = generativeModel.generateContent(prompt)
                _textGenerationResult.value = result.text
                speekLoud(result.text!!)
            } catch (e: Exception) {
                _textGenerationResult.value = "Error: ${e.message}"
            }
        }
    }


    fun initTTS(context: Context) {
        speechRecognizer = android.speech.SpeechRecognizer
            .createSpeechRecognizer(context)
        speechRecognizer.setRecognitionListener(MySpeechRecognizer())

        textToSpeech = TextToSpeech(context, this)
    }

    fun startSpeechRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM,
        )
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
        //        "hu-HU");
        intent.putExtra(
            RecognizerIntent.EXTRA_CALLING_PACKAGE,
            "hu.ait.texttospeechspeechrecognition"
        )

        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5)
        speechRecognizer.startListening(intent)
    }

    fun speekLoud(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null)
    }

    fun stopSpeak() {
        textToSpeech.stop()
    }

    override fun onInit(p0: Int) {
        //speekLoud("Speech system works perfectly")
    }

    internal inner class MySpeechRecognizer : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle) {
            Log.d(TAG, "onReadyForSpeech")
        }

        override fun onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech")
        }

        override fun onRmsChanged(rmsdB: Float) {
            Log.d(TAG, "onRmsChanged")
        }

        override fun onBufferReceived(buffer: ByteArray) {
            Log.d(TAG, "onBufferReceived")
        }

        override fun onEndOfSpeech() {
            Log.d(TAG, "onEndofSpeech")
        }

        override fun onError(error: Int) {
            Log.d(TAG, "error $error")
        }

        override fun onResults(results: Bundle) {
            Log.d(TAG, "onResults $results")
            try {
                val data = results
                    .getStringArrayList(
                        android.speech.SpeechRecognizer.RESULTS_RECOGNITION
                    )
                if (data!!.size > 0) {
                    detectedText.value = data!!.get(0)
                    Log.d(TAG, "onResults ${detectedText.value}")
                    generateAnswer(prompt = data!!.get(0))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun onPartialResults(partialResults: Bundle) {
            Log.d(TAG, "onPartialResults")
            val data = partialResults
                .getStringArrayList(
                    android.speech.SpeechRecognizer.RESULTS_RECOGNITION
                )
            detectedText.value = ""
            for (text in data!!.iterator()) {
                detectedText.value += "$text \n"
            }
        }

        override fun onEvent(eventType: Int, params: Bundle) {
            Log.d(TAG, "onEvent $eventType")
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[
                    ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as Application)
                GenAIViewModel(application)
            }
        }
    }

}