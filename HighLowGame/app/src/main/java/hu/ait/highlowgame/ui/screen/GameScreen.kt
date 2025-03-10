package hu.ait.highlowgame.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    var myGuess by remember { mutableStateOf("0") }
    var result by remember { mutableStateOf("-") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "$myGuess",
            onValueChange = {
                myGuess = it
            },
            label = { Text("Enter your guess here") },
            isError = true,
            keyboardOptions =
                KeyboardOptions(
                    keyboardType =
                    KeyboardType.Number
                )

        )
        Button(onClick = {
            try {

                val myNum = myGuess.toInt()
                if (myNum == viewModel.generatedNumber.value) {
                    result = "Well done, you have won!"
                } else if (myNum > viewModel.generatedNumber.value) {
                    result = "The number is lower"
                } else if (myNum < viewModel.generatedNumber.value) {
                    result = "The number is higher"
                }
            }catch (e: Exception) {
                result = "Error: ${e.message}"
            }

        }) {
            Text("Guess")
        }

        Text(
            "$result",
            fontSize = 30.sp
        )
    }
}