package com.norvellium.tasky.auth.presentation.login

import android.app.Application
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.norvellium.tasky.R
import com.norvellium.tasky.core.validation.ValidateEmail
import com.norvellium.tasky.core.validation.ValidatePassword
import com.norvellium.tasky.core.validation.ValidationResult
import com.norvellium.tasky.auth.data.local.TokenPreferences
import com.norvellium.tasky.auth.data.local.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val authRepository: AuthRepository,
    private val tokenPreferences: TokenPreferences,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val resources = application.resources
    private val email = savedStateHandle.getStateFlow("email", "")
    private val password = savedStateHandle.getStateFlow("password", "")

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    fun checkIfEmailValid(text: Editable) {
        _loginState.update {
            it.copy(
                email = text.toString(),
                isValidEmail = validateEmail.validate(text.toString()) == ValidationResult.SUCCESSFUL
            )
        }
    }

    fun updatePassword(text: Editable) {
        _loginState.update {
            it.copy(
                password = text.toString(),
            )
        }
    }

    fun togglePasswordVisibility() {
        _loginState.update {
            it.copy(isPasswordVisible = !loginState.value.isPasswordVisible)
        }
    }

    fun validateLogin() {
        val emailResult = validateEmail.validate(loginState.value.email)
        val passwordResult = validatePassword.validate(loginState.value.password)

        val validationResults = listOf(emailResult, passwordResult)
        val hasError = validationResults.any { it != ValidationResult.SUCCESSFUL }

        if (hasError) {
            _loginState.update { it ->
                it.copy(errorMessage = resolveErrorMessage(validationResults.first { it != ValidationResult.SUCCESSFUL }))
            }
            viewModelScope.launch {
                eventChannel.send(
                    LoginEvent.ValidationFailed(
                        loginState.value.errorMessage
                    )
                )
            }
        } else {
            viewModelScope.launch {
                eventChannel.send(LoginEvent.ValidationSuccess)
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            try {
                val response = authRepository.login(loginState.value.email!!, loginState.value.password!!)
                response?.let { tokenPreferences.writeToken(it.token) }
                eventChannel.send(LoginEvent.LoginSucceeded)
            } catch (e: Exception) {
                eventChannel.send(LoginEvent.LoginFailed(e.message))
            }
        }
    }

    private fun resolveErrorMessage(validationResult: ValidationResult): String? {
        return when (validationResult) {
            ValidationResult.EMAIL_BLANK -> resources.getString(R.string.validation_error_email_blank)
            ValidationResult.EMAIL_INVALID -> resources.getString(R.string.validation_error_email_invalid)
            ValidationResult.PASSWORD_TOO_SHORT -> resources.getString(R.string.validation_error_password_length)
            ValidationResult.PASSWORD_BLANK -> resources.getString(R.string.validation_error_password_empty)
            ValidationResult.PASSWORD_NO_LOWERCASE -> resources.getString(R.string.validation_error_password_no_lowercase)
            ValidationResult.PASSWORD_NO_UPPERCASE -> resources.getString(R.string.validation_error_password_no_uppercase)
            ValidationResult.PASSWORD_NO_NUMBER -> resources.getString(R.string.validation_error_password_no_number)
            ValidationResult.USERNAME_INVALID -> resources.getString(R.string.validation_error_username_invalid_length)
            ValidationResult.SUCCESSFUL -> null
        }
    }
}