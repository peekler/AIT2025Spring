package hu.ait.httpdemo.ui.screen.money

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ait.httpdemo.data.MoneyResult
import hu.ait.httpdemo.network.MoneyAPI
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MoneyViewModel @Inject constructor(
    val moneyAPI: MoneyAPI
) : ViewModel() {

    var moneyUiState: MoneyUiState by mutableStateOf(MoneyUiState.Init)

    fun getRates() {
        moneyUiState = MoneyUiState.Loading
        viewModelScope.launch {
            moneyUiState = try {
                val result = moneyAPI.getRates("969c37b5a73f8cb2d12c081dcbdc35e6")
                MoneyUiState.Success(result)
            } catch (e: IOException) {
                MoneyUiState.Error
            } catch (e: HttpException) {
                MoneyUiState.Error
            } catch (e: Exception) {
                MoneyUiState.Error
            }
        }
    }
}

sealed interface MoneyUiState {
    object Init : MoneyUiState
    data class Success(val moneyRates: MoneyResult) : MoneyUiState
    object Error : MoneyUiState
    object Loading : MoneyUiState
}