package hu.ait.highlowgame.ui.screen.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun GameScreen(modifier: Modifier = Modifier,
    viewModel: GameViewModel = viewModel()
) {
    var myNumber by rememberSaveable { mutableStateOf("0") }

    var resultText by rememberSaveable { mutableStateOf("-") }
    var errorText by rememberSaveable { mutableStateOf("-") }
    var inputErrorState by rememberSaveable { mutableStateOf(false) }

    var showWinDialog by rememberSaveable { mutableStateOf(false) }

    fun validateInput(input: String) {
        try {
            val myNum = input.toInt()
            inputErrorState = false
        } catch (e: Exception) {
            errorText = e.localizedMessage
            inputErrorState = true
        }
    }

    Column(modifier = modifier.padding(start = 20.dp, end = 20.dp)) {
        Text(
            "HighLowGame",
            fontSize = 28.sp
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Enter your guess here") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),

            value = "$myNumber",
            onValueChange = {
                //if (it.length <=3) {
                myNumber = it
                validateInput(myNumber)
                //}
            },
            isError = inputErrorState,
            supportingText = {
                if (inputErrorState) {
                    Text(errorText)
                }
            },
            trailingIcon = {
                if (inputErrorState)
                    Icon(Icons.Filled.Warning, "error",
                        tint = MaterialTheme.colorScheme.error)
            },
            leadingIcon = {
                Icon(Icons.Filled.Menu, "error")
            }
        )


        Button(
            onClick = {
                //

                if (myNumber.toInt() == viewModel.generatedNum) {
                    resultText = "Well done, you won!"
                    viewModel.generateRandomNumber()
                    viewModel.counter = 0
                    showWinDialog = true
                } else if (myNumber.toInt() > viewModel.generatedNum) {
                    resultText = "The number is lower"
                    viewModel.increaceCounter()
                } else if (myNumber.toInt() < viewModel.generatedNum) {
                    resultText = "The number is higher"
                    viewModel.increaceCounter()
                }
            },
            enabled = myNumber.isNotEmpty()
        ) {
            Text("Guess (${viewModel.counter})")
        }


        Text(
            "Result: $resultText",
            fontSize = 24.sp
        )
    }

    if (showWinDialog) {
        WinDialog(
            onConfirm = {
                showWinDialog = false
            },
            onDismiss = {
                showWinDialog = false
            }
        )
    }
}

@Composable
fun WinDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Well done!") },
        text = { Text("You have won!") },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Ok")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}