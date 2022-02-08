package com.example.aclite.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aclite.R
import com.example.aclite.data.LoginRepository
import com.example.aclite.data.success
import com.example.aclite.domain.TryLoginUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(
    private val tryLoginUseCase: TryLoginUseCase = TryLoginUseCase()
) : ViewModel(){

    private val _state = MutableLiveData<UiState>()
    val state : LiveData<UiState> get() = _state

    sealed class UiStateOld{
        object Loading : UiStateOld()
        data class Error(val userErrorString: String, val passError : String) : UiStateOld()
        object LoggedIn : UiStateOld()
    }

    data class UiState(
        val loggingIn : Boolean = false,
        val loggedIn : Boolean = false,
        val userError : Int? = null,
        val passError: Int? = null
    )

    fun onTryLogin(user : String,pass: String){
        viewModelScope.launch {
            _state.value = UiState(loggingIn = true)
            //tryLoginOld(user,pass)

            val result = tryLoginUseCase(user, pass) //en vez de llamar el invoke, como es un operator, no es necesario

            _state.value = UiState(
                userError = if(result.userError) R.string.username_invalid else null,
                passError = if(result.passError) R.string.password_invalid else null,
                loggedIn = result.success
            )
        }
    }

    @Deprecated(message = "Se utiliza el metodo login en el repository")
    private suspend fun tryLoginOld(user : String, pass: String) {
        delay(2000)
        val userError = if(!user.contains("@")) R.string.username_invalid else null
        val passError = if(pass.length < 5) R.string.password_invalid else null
        val loggedIn = (userError == null && passError == null)

        _state.value = UiState(loggedIn = loggedIn,userError = userError, passError = passError)
    }

    fun onNavigateToNextScreen() {
        _state.value = requireNotNull(state.value).copy(loggedIn = false)
    }
}