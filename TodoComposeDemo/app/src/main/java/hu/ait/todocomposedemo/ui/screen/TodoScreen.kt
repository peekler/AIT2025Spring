package hu.ait.todocomposedemo.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hu.ait.todocomposedemo.data.TodoItem

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import hu.ait.todocomposedemo.data.TodoPriority
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    viewModel: TodoViewModel = hiltViewModel(),
    onInfoClicked : (Int, Int) -> Unit
) {
    var todoTitle by rememberSaveable { mutableStateOf("") }

    var showTodoDialg by rememberSaveable { mutableStateOf(false) }

    var todoToEdit: TodoItem? by rememberSaveable {
        mutableStateOf(null)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo List") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.clearAllTodos()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete all"
                        )
                    }
                    IconButton(
                        onClick = {
                            showTodoDialg = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = "Add"
                        )
                    }
                    IconButton(
                        onClick = {
                            onInfoClicked(
                                viewModel.getAllTodoNum(),
                                viewModel.getImportantTodoNum()
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Summary"
                        )
                    }
                }
            )
        }

    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            if (showTodoDialg) {
                TodoDialog(
                    viewModel = viewModel,
                    todoToEdit = todoToEdit,
                    onCancel = {
                        showTodoDialg = false
                    }
                )
            }


            if (viewModel.getAllToDoList().size == 0) {
                Text("No todos")
            } else {
                LazyColumn {
                    items(viewModel.getAllToDoList()) { todoItem ->
                        //Text(text = "${todoItem.title} - ${todoItem.createDate}")

                        TodoCard(
                            todoItem = todoItem,
                            onCheckedChange = { checkBoxState ->
                                viewModel.changeTodoState(todoItem, checkBoxState)
                            },
                            onDelete = {
                                viewModel.removeTodoItem(todoItem)
                            },
                            onEdit = {
                                todoItemToEdit ->
                                    todoToEdit = todoItemToEdit
                                    showTodoDialg = true
                            }
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun TodoCard(
    todoItem: TodoItem,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit,
    onEdit: (TodoItem) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var todoChecked by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
                .animateContentSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = todoItem.priority.getIcon()),
                    contentDescription = "Priority",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 10.dp)
                )

                Text(todoItem.title, modifier = Modifier.fillMaxWidth(0.2f))
                Spacer(modifier = Modifier.fillMaxSize(0.45f))
                Checkbox(
                    checked = todoItem.isDone,
                    onCheckedChange = { onCheckedChange(it) }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.clickable {
                        onDelete()
                    },
                    tint = Color.Red
                )
                Icon(
                    imageVector = Icons.Filled.Build,
                    contentDescription = "Edit",
                    modifier = Modifier.clickable {
                        onEdit(todoItem)
                    },
                    tint = Color.Blue
                )
                IconButton(
                    onClick = {
                        expanded = !expanded
                    }
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp
                        else Icons.Filled.KeyboardArrowDown,
                        contentDescription = "Expand or close"
                    )
                }
            }

            if (expanded) {
                Text(
                    todoItem.description,
                    style = TextStyle(
                        fontSize = 12.sp

                    )
                )
                Text(
                    todoItem.createDate,
                    style = TextStyle(
                        fontSize = 12.sp

                    )
                )
            }
        }
    }
}

@Composable
fun TodoDialog(
    viewModel: TodoViewModel,
    todoToEdit: TodoItem? = null,
    onCancel: () -> Unit
) {
    var todoTitle by remember {
        mutableStateOf(
            todoToEdit?.title ?: ""
        )
    }
    var todoDesc by remember {
        mutableStateOf(
            todoToEdit?.description ?: ""
        )
    }
    var important by remember {
        mutableStateOf(
            if (todoToEdit != null) {
                todoToEdit.priority == TodoPriority.HIGH
            } else {
                false
            }
        )
    }

    Dialog(onDismissRequest = {
        onCancel()
    }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Text(
                    if (todoToEdit == null) "New Todo" else "Edit Todo",
                    style = MaterialTheme.typography.titleMedium
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Todo title") },
                    value = "$todoTitle",
                    onValueChange = { todoTitle = it })
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Todo description") },
                    value = "$todoDesc",
                    onValueChange = { todoDesc = it })
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = important,
                        onCheckedChange = { important = it })
                    Text("Important")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        if (todoToEdit == null) {
                            viewModel.addTodoList(
                                TodoItem(
                                    id = 0,
                                    title = todoTitle,
                                    description = todoDesc,
                                    createDate = Date(System.currentTimeMillis()).toString(),
                                    priority = if (important) TodoPriority.HIGH else TodoPriority.NORMAL,
                                    isDone = false
                                )
                            )
                        } else {
                            val editedTodo = todoToEdit.copy(
                                title = todoTitle,
                                description = todoDesc,
                                priority = if (important) TodoPriority.HIGH else TodoPriority.NORMAL
                            )
                            viewModel.editTodoItem(
                                todoToEdit,
                                editedTodo)
                        }


                        onCancel()
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}