package hu.ait.highlowgame.ui.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import hu.ait.highlowgame.ui.navigation.GameScreen

class GameViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var generatedNumber = mutableStateOf(0)

    var counter = mutableStateOf(0)

    var upperBound = 0

    init {
        upperBound = savedStateHandle.toRoute<GameScreen>().upperBound
        generateRandomNumber(upperBound)
    }

    fun generateRandomNumber(upperBound: Int) {
        generatedNumber.value = (0..upperBound).random()
    }

    fun increaseCounter() {
        counter.value++
    }

    fun reset() {
        counter.value = 0
        generateRandomNumber(upperBound)
    }



}