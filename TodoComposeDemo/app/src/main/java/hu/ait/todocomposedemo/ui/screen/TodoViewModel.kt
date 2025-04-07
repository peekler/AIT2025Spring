package hu.ait.todocomposedemo.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ait.todocomposedemo.data.TodoDAO
import hu.ait.todocomposedemo.data.TodoItem
import hu.ait.todocomposedemo.data.TodoPriority
import javax.inject.Inject

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class TodoViewModel @Inject constructor(val todoDAO: TodoDAO) : ViewModel() {

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