package hu.ait.highlowgame.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }

    var myGuess by remember { mutableStateOf("0") }
    var result by remember { mutableStateOf("-") }

    val context = LocalContext.current
    var inputErrorState by rememberSaveable { mutableStateOf(false) }
    var errorText by rememberSaveable {
        mutableStateOf("Error in the input")
    }

    fun validate(text: String) {
        val allDigit = text.all { char -> char.isDigit() }

        errorText = "This field can be number only"
        inputErrorState = !allDigit
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        SimpleAlertDialog(showDialog,
            onConfirm = { showDialog = false },
            onDismiss = { showDialog = false }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "$myGuess",
            onValueChange = {
                myGuess = it
                validate(it)
            },
            label = { Text("Enter your guess here") },
            isError = inputErrorState,
            keyboardOptions =
                KeyboardOptions(
                    keyboardType =
                    KeyboardType.Number
                ),
            trailingIcon = {
                if (inputErrorState) {
                    Icon(
                        Icons.Filled.Warning,
                        "error", tint = MaterialTheme
                            .colorScheme.error
                    )
                }
            }
        )
        if (inputErrorState) {
            Text(
                text = "$errorText",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Button(onClick = {
            try {
                val myNum = myGuess.toInt()
                if (myNum == viewModel.generatedNumber.value) {
                    result = "Well done, you have won!, counter: ${viewModel.counter.value}"

                    showDialog = true
                } else if (myNum > viewModel.generatedNumber.value) {
                    viewModel.increaseCounter()
                    result = "The number is lower, counter: ${viewModel.counter.value}"
                } else if (myNum < viewModel.generatedNumber.value) {
                    viewModel.increaseCounter()
                    result = "The number is higher, counter: ${viewModel.counter.value}"
                }
            } catch (e: Exception) {
                result = "Error: ${e.message}"
            }

        }) {
            Text("Guess")
        }

        Button(onClick = {
            result = "-"
            viewModel.reset()
        }) {
            Text("Reset")
        }

        Text(
            "$result",
            fontSize = 30.sp
        )
    }
}


@Composable
fun SimpleAlertDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Congratulations!") },
            text = { Text(text = "You have won!") },
            confirmButton = {
                TextButton(onClick = onConfirm)
                { Text(text = "OK") }
            },
            dismissButton = {
                TextButton(onClick = onDismiss)
                { Text(text = "Cancel") }
            }
        )
    }
}

