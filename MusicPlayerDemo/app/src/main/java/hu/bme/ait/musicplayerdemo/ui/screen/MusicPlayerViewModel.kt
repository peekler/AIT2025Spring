package hu.bme.ait.musicplayerdemo.ui.screen

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.ait.musicplayerdemo.R
import java.util.*

class MusicPlayerViewModel(
    private val context: Context
) : ViewModel() {

    var mediaPlayerPrepared = mutableStateOf(false)
    var duration = mutableStateOf(0)
    var mediaProgress = mutableStateOf(0f)


    val mediaPlayer = MediaPlayer.create(context, R.raw.demo)

    init {
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.setVolume(50f,50f)
            duration.value = mediaPlayer.duration
            mediaPlayerPrepared.value = true
        }
    }

    private var timer: Timer? = null
    private var mySeekTimerTask: MySeekTimerTask? = null

    fun startPlayer() {
        mediaPlayer.start()
        if (timer != null) {
            timer?.cancel()
        }
        timer = Timer()
        mySeekTimerTask = MySeekTimerTask()
        timer?.schedule(mySeekTimerTask,0, 1000)
    }

    inner class MySeekTimerTask: TimerTask() {
        override fun run() {
            mediaProgress.value = mediaPlayer.currentPosition.toFloat()
        }
    }

    override fun onCleared() {
        try {
            mediaPlayer.stop()
        }catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            timer?.cancel()
        }catch (e: Exception) {
            e.printStackTrace()
        }
        super.onCleared()
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[
                    ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as Application)
                MusicPlayerViewModel(application)
            }
        }
    }

}