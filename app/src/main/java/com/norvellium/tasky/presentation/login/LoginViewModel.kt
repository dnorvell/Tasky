package com.norvellium.tasky.presentation.login

import android.text.Editable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norvellium.tasky.core.validation.ValidateEmail
import com.norvellium.tasky.core.validation.ValidatePassword
import com.norvellium.tasky.preferences.TokenPreferences
import com.norvellium.tasky.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenPreferences: TokenPreferences,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val email = savedStateHandle.getStateFlow("email", "")
    private val password = savedStateHandle.getStateFlow("password", "")

//    private val _loginState = MutableStateFlow(LoginState())

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    //    val loginState = combine(email, password) { email, password ->
    private val _loginState = MutableStateFlow(
        LoginState(
//            email = email,
//            password = password,
//            isValidEmail = validateEmail.validate(email).successful,
//            isValidPassword = validatePassword.validate(password).successful
        )
    )
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LoginState())

    val loginState = _loginState.asStateFlow()


    fun checkIfEmailValid(text: Editable) {
        _loginState.update {
            it.copy(
            email = text.toString(),
            isValidEmail = validateEmail.validate(text.toString()).successful)
        }
    }

    fun login() {
        val emailResult = validateEmail.validate(loginState.value.email!!)
        val passwordResult = validatePassword.validate(loginState.value.password!!)

        val hasError = listOf(emailResult, passwordResult).any { !it.successful }

        if (hasError) {
            listOf(
                emailResult,
                passwordResult
            ).first { !it.successful }.errorMessage?.let {
                // TODO Update state with error message
                viewModelScope.launch {
                    eventChannel.send(LoginEvent.ValidationFailed(it))
                }
                return
            }
        }

        viewModelScope.launch {
            eventChannel.send(LoginEvent.ValidationSuccess)
//            try {
//                val response = authRepository.login(loginState.value.email, loginState.value.password)
//                tokenPreferences.writeToken(response.token)
//                // TODO Save token on success and navigate
//            }
//            // TODO additional exception type
//            catch (e: Exception) {
//                // TODO handle errors
//            }
        }
    }
}