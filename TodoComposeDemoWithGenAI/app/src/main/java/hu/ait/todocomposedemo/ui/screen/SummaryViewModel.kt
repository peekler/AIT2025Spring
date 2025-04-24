package hu.ait.todocomposedemo.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import hu.ait.todocomposedemo.ui.navigation.SummaryScreenRoute

class SummaryViewModel(
    savedStateHandle: SavedStateHandle) : ViewModel() {

    var allTodo by mutableStateOf(0)
    var importantTodo by mutableStateOf(0)


    init {
        allTodo = savedStateHandle.toRoute<SummaryScreenRoute>().allTodos
        importantTodo = savedStateHandle.toRoute<SummaryScreenRoute>().importantTodos
    }
}