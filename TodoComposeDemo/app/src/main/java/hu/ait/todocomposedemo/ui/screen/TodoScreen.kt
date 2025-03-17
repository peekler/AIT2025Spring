package hu.ait.todocomposedemo.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hu.ait.todocomposedemo.data.TodoItem

import androidx.compose.ui.res.painterResource
import hu.ait.todocomposedemo.data.TodoPriority
import java.util.Date

@Composable
fun TodoScreen(
    viewModel: TodoViewModel = viewModel()
) {
    var todoTitle by rememberSaveable { mutableStateOf("") }

    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Enter todo here") },
            value = todoTitle,
            onValueChange = {
                todoTitle = it
            }
        )
        Button(
            onClick = {
                viewModel.addTodoList(
                    TodoItem(
                        "",
                        todoTitle,
                        "Description",
                        Date(System.currentTimeMillis()).toString(),
                        TodoPriority.NORMAL,
                        false
                    )
                )
            }
        ) {
            Text("Add todo")
        }


        if (viewModel.getAllToDoList().size==0) {
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
                        onEdit = {}
                    )
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
)
{
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
        Row(
            modifier = Modifier
                .padding(20.dp)
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
            Spacer(modifier = Modifier.fillMaxSize(0.55f))
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
        }
    }
}