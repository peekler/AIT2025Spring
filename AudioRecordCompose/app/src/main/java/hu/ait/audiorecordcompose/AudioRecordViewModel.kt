package hu.ait.audiorecordcompose

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import java.io.File
import java.io.IOException

class AudioRecordViewModel(private var context: Context) : ViewModel() {

    private var myPlayer: MediaPlayer? = null
    private var myRecorder: MediaRecorder? = null

    fun startPlaying() {
        myPlayer = MediaPlayer()
        try {
            myPlayer?.setDataSource(context.openFileInput("audiorecordtest.3gp").fd)
            myPlayer?.setOnPreparedListener {
                myPlayer?.start()
            }
            myPlayer?.prepare()
        } catch (e: IOException) {
            Log.e("TAG_AUDIO_RECORD", "prepare() failed")
        }
    }

    fun stopPlaying() {
        myPlayer?.release()
    }

    fun startRecording() {
        try {
            myRecorder = MediaRecorder()
            myRecorder?.setAudioSource(
                MediaRecorder.AudioSource.MIC
            )
            myRecorder?.setOutputFormat(
                MediaRecorder.OutputFormat.THREE_GPP
            )

            myRecorder?.setOutputFile(context.openFileOutput("audiorecordtest.3gp", Context.MODE_PRIVATE).fd)

            myRecorder?.setAudioEncoder(
                MediaRecorder.AudioEncoder.AMR_NB
            )
            myRecorder?.prepare()
            myRecorder?.start()
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("TAG_AUDIO_RECORD", "prepare() failed")
        }
    }

    fun stopRecording() {
        myRecorder?.stop()
        myRecorder?.release()
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[
                        ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as Application)
                AudioRecordViewModel(application)
            }
        }
    }

}