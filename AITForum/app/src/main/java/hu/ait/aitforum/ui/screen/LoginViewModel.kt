package hu.ait.aitforum.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {
    var loginUiState: LoginUiState by mutableStateOf(LoginUiState.Init)

    private lateinit var auth: FirebaseAuth

    init {
        auth = Firebase.auth
    }

    fun registerUser(email: String, password: String) {
        loginUiState = LoginUiState.Loading
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    loginUiState = LoginUiState.RegisterSuccess
                }
                .addOnFailureListener {
                    loginUiState = LoginUiState.Error(it.localizedMessage)
                }
        } catch (e: Exception) {
            loginUiState = LoginUiState.Error(e.localizedMessage)
            e.printStackTrace()
        }
    }

    suspend fun loginUser(email: String, password: String): AuthResult? {
        loginUiState = LoginUiState.Loading
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            if (result.user != null) {
                loginUiState = LoginUiState.LoginSuccess
            } else {
                loginUiState = LoginUiState.Error("Login failed")
            }

            return result
        } catch (e: Exception) {
            loginUiState = LoginUiState.Error(e.localizedMessage)
            e.printStackTrace()
            return null
        }
    }

}


sealed interface LoginUiState {
    object Init: LoginUiState
    object Loading: LoginUiState
    object RegisterSuccess: LoginUiState
    object LoginSuccess: LoginUiState
    data class Error(val errorMessage: String?): LoginUiState
}