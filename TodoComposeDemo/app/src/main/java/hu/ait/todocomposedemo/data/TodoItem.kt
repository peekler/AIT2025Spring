package hu.ait.todocomposedemo.data

import hu.ait.todocomposedemo.R

data class TodoItem(
    val id: String,
    val title:String,
    val description:String,
    val createDate:String,
    var priority:TodoPriority,
    var isDone: Boolean
)

enum class TodoPriority {
    NORMAL, HIGH;

    fun getIcon(): Int {
        return if (this == NORMAL) R.drawable.normal else R.drawable.important
    }
}
