package hu.ait.aitforum.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

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

    fun loginUser(email: String, password: String) {
        loginUiState = LoginUiState.Loading
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    loginUiState = LoginUiState.LoginSuccess
                }
                .addOnFailureListener {
                    loginUiState = LoginUiState.Error(it.localizedMessage)
                }
        } catch (e: Exception) {
            loginUiState = LoginUiState.Error(e.localizedMessage)
            e.printStackTrace()
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