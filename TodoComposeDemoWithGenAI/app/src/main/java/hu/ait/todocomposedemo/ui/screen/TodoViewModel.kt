package hu.ait.todocomposedemo.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ait.todocomposedemo.data.TodoDAO
import hu.ait.todocomposedemo.data.TodoItem
import hu.ait.todocomposedemo.data.TodoPriority
import javax.inject.Inject

import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

@HiltViewModel
class TodoViewModel @Inject constructor(val todoDAO: TodoDAO) : ViewModel() {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = "YOUR_KEY_HERE"
    )

    private val _textGenerationResult = MutableStateFlow<String?>(null)
    val textGenerationResult = _textGenerationResult.asStateFlow()

    fun generateTodoItems(
        todoAboutInput: String
        ) {
        _textGenerationResult.value = "Generating"
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val prompt = "Generate todo items for a $todoAboutInput project, maximum 4 items in the list. Each item should be short and high level, maximum 20 character long." +
                "Do not generate any special explanation just the pure todo list in a format that each item is separated with a # symbol." +
                        "I want to use the result text in a parser that splits the result by # and adds the items to a list without further parsing."

                val result = generativeModel.generateContent(prompt)
                _textGenerationResult.value = result.text
                val todoResults = result.text!!.split("#")
                for (todoResult in todoResults) {
                    addTodoList(
                        TodoItem(
                            0,
                            todoResult,
                            "Desc",
                            Date(System.currentTimeMillis()).toString(),
                            TodoPriority.NORMAL,
                            false
                        )
                    )
                }
            } catch (e: Exception) {
                _textGenerationResult.value = "Error: ${e.message}"
            }
        }
    }

    fun getAllToDoList(): Flow<List<TodoItem>> {
        return todoDAO.getAllTodos()
    }

    suspend fun getAllTodoNum(): Int {
        return todoDAO.getTodosNum()
    }

    suspend fun getImportantTodoNum(): Int {
        return todoDAO.getImportantTodosNum()
    }

    fun addTodoList(todoItem: TodoItem) {
        // launch: launch a new coroutine in the scope of the current ViewModel
        viewModelScope.launch() {
            todoDAO.insert(todoItem)
        }
    }

    fun removeTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            todoDAO.delete(todoItem)
        }
    }

    fun editTodoItem(editedTodo: TodoItem) {
        viewModelScope.launch {
            todoDAO.update(editedTodo)
        }
    }

    fun changeTodoState(todoItem: TodoItem, value: Boolean) {
        // because copy makes a new instance,
        // this will trigger the state change in the table
        val updatedTodo = todoItem.copy()
        updatedTodo.isDone = value
        viewModelScope.launch {
            todoDAO.update(updatedTodo)
        }
    }

    fun clearAllTodos() {
        viewModelScope.launch {
            todoDAO.deleteAllTodos()
        }
    }
}