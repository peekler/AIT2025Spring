package hu.ait.httpdemo.ui.screen.money

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import hu.ait.httpdemo.data.MoneyResult

@Composable
fun MoneyScreen(
    viewModel: MoneyViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = {
            viewModel.getRates()
        }) {
            Text(text = "Refresh")
        }

        when (viewModel.moneyUiState) {
            is MoneyUiState.Init -> Text("Press refresh to do something...")
            is MoneyUiState.Loading -> CircularProgressIndicator()
            is MoneyUiState.Success -> MoneyResultWidget(
                (viewModel.moneyUiState as MoneyUiState.Success).moneyRates)
            is MoneyUiState.Error -> Text(text = "Error...")
        }
    }
}


@Composable
fun MoneyResultWidget(moneyRates: MoneyResult) {
    Column() {
        Text(text = "Base: EUR")
        Text(text = "USD: ${moneyRates.rates?.uSD}")
        Text(text = "EUR: ${moneyRates.rates?.eUR}")
        Text(text = "HUF: ${moneyRates.rates?.hUF}")
        Text(text = "GBP: ${moneyRates.rates?.gBP}")
    }
}
