package hu.ait.texttospeechspeechrecognition

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
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class TTSViewModel(private var context: Context) : ViewModel(), TextToSpeech.OnInitListener {

    val TAG = "TAG_TTS_SPEECH"

    private lateinit var speechRecognizer: android.speech.SpeechRecognizer

    private lateinit var textToSpeech: TextToSpeech

    var detectedText = mutableStateOf("")

    init {
        initTTS(context)
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
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,true)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "hu-HU");
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

    override fun onInit(p0: Int) {
        speekLoud("Speech system works perfectly")
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
            val data = results
                .getStringArrayList(
                    android.speech.SpeechRecognizer.RESULTS_RECOGNITION
                )
            detectedText.value = ""
            var timeDetected = false
            var sureDetected = false

            for (text in data!!.iterator()) {
                detectedText.value += "$text \n"

                Log.d(TAG, "onResults ${detectedText.value}")

                if (!timeDetected && text.contains("time")) {
                    val dateFormat = SimpleDateFormat(
                        "HH:mm:ss"
                    )
                    speekLoud(dateFormat.format(Date(System.currentTimeMillis())))
                    timeDetected = true
                }

                if (!sureDetected && text.contains("sure")) {
                    speekLoud("Yes, I'm sure, I'm perfect")
                    sureDetected = true
                }
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
                TTSViewModel(application)
            }
        }
    }
}