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
import kotlinx.coroutines.launch

@HiltViewModel
class TodoViewModel @Inject constructor(val todoDAO: TodoDAO) : ViewModel() {

    private var _todoList = mutableStateListOf<TodoItem>()

    init {
        repeat(0)
        {
            _todoList.add(
                TodoItem(
                    0,
                    "Todo $it", "Description $it",
                    "17.03.2025.", TodoPriority.NORMAL, false
                )
            )
        }
    }

    fun getAllToDoList(): List<TodoItem> {
        return _todoList
    }

    fun getAllTodoNum(): Int {
        return _todoList.size
    }

    fun getImportantTodoNum(): Int {
        return _todoList.count{it.priority==TodoPriority.HIGH}
    }

    fun addTodoList(todoItem: TodoItem) {
        _todoList.add(todoItem)

        viewModelScope.launch() {
            todoDAO.insert(todoItem)
        }
    }

    fun removeTodoItem(todoItem: TodoItem) {
        _todoList.remove(todoItem)
    }

    fun editTodoItem(originalTodo: TodoItem, editedTodo: TodoItem) {
        val index = _todoList.indexOf(originalTodo)
        _todoList[index] = editedTodo
    }

    fun changeTodoState(todoItem: TodoItem, value: Boolean) {
        val index = _todoList.indexOf(todoItem)

        val newTodo = todoItem.copy(
            title = todoItem.title,
            description = todoItem.description,
            createDate = todoItem.createDate,
            priority = todoItem.priority,
            isDone = value
        )

        _todoList[index] = newTodo
    }

    fun clearAllTodos() {
        _todoList.clear()
    }
}