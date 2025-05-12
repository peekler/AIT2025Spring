package hu.ait.highlowgame.ui.screen.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlin.math.max

class GameViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    var generatedNum by mutableStateOf(0)
    var counter by mutableStateOf(0)
    var maxNumber = 3

    init {
        var maxArg = savedStateHandle.get<String>("maxnum") ?: "3"
        maxNumber = maxArg.toInt()
        generateRandomNumber()
    }

    fun generateRandomNumber() {
        generatedNum = (1..maxNumber).random()
    }

    fun increaceCounter() {
        counter++
    }


}