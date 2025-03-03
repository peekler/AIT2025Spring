package hu.bme.aut.fruitslicerdemocanvas.ui.screen

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.fruitslicerdemocanvas.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random

class GameViewModel : ViewModel(){

    companion object {
        const val FRUIT_RADIUS = 60f
    }

    var points by mutableStateOf<List<Offset>>(emptyList())
    var fruitPoints by  mutableStateOf<List<Offset>>(emptyList())
    var gameRunning = false

    var mediaSword: MediaPlayer? = null
    var mediaSplash: MediaPlayer? = null
    var mediaSwordPrepared = false
    var mediaSplashPrepared = false

    fun initMedia(context: Context) {
        mediaSword = MediaPlayer.create(context, R.raw.sword)
        mediaSplash = MediaPlayer.create(context, R.raw.splash)
        mediaSword?.setOnPreparedListener {
            mediaSwordPrepared = true
        }
        mediaSplash?.setOnPreparedListener {
            mediaSplashPrepared = true
        }
    }

    fun playSword() {
        if (mediaSwordPrepared) {
            if (mediaSword!!.isPlaying) {
                mediaSword?.seekTo(0)
            } else {
                mediaSword?.start()
            }
        }
    }

    fun playSplash() {
        if (mediaSplashPrepared) {
            if (mediaSplash!!.isPlaying) {
                mediaSplash?.seekTo(0)
            } else {
                mediaSplash?.start()
            }
        }
    }

    fun startGame(xMax: Int, yMax: Int) {
        gameRunning = true
        viewModelScope.launch {
            val rand = Random(System.currentTimeMillis())
            while (gameRunning) {
                fruitPoints += Offset(40+rand.nextInt(xMax).toFloat(),
                    40+rand.nextInt(yMax).toFloat())

                delay(2000)
            }
        }
    }

    fun splashFruits() : Boolean {
        var remainingFruits = emptyList<Offset>()
        var wasSplash = false
        fruitPoints.forEach {fruitPoint->
            var fruitSplash = false
            for (swordPoint in points) {
                val dist = (swordPoint-fruitPoint).getDistance()
                if (dist < FRUIT_RADIUS) {
                    fruitSplash = true
                    wasSplash = true
                    break
                }
            }
            if (!fruitSplash) {
                remainingFruits += fruitPoint
            }
        }
        fruitPoints = remainingFruits
        return wasSplash
    }

    fun stopGame() {
        gameRunning = false
    }

    override fun onCleared() {
        gameRunning = false
        super.onCleared()
    }

}