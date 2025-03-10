package hu.ait.highlowgame.ui.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    var generatedNumber = mutableStateOf(0)

    init {
        generateRandomNumber()
    }

    fun generateRandomNumber() {
        generatedNumber.value = (0..3).random()
    }

}