package com.norvellium.tasky.auth.presentation.register

import android.app.Application
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norvellium.tasky.R
import com.norvellium.tasky.auth.data.local.AuthRepository
import com.norvellium.tasky.core.util.UiText
import com.norvellium.tasky.core.validation.ValidateEmail
import com.norvellium.tasky.core.validation.ValidatePassword
import com.norvellium.tasky.core.validation.ValidateUsername
import com.norvellium.tasky.core.validation.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validateUsername: ValidateUsername,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()

    fun checkIfUsernameValid(text: Editable) {
        _registerState.update {
            it.copy(
                username = text.toString(),
                isValidUsername = validateUsername.validate(text.toString()) == ValidationResult.SUCCESSFUL
            )
        }
    }

    fun checkIfEmailValid(text: Editable) {
        _registerState.update {
            it.copy(
                email = text.toString(),
                isValidEmail = validateEmail.validate(text.toString()) == ValidationResult.SUCCESSFUL
            )
        }
    }

    fun updatePassword(text: Editable) {
        _registerState.update {
            it.copy(
                password = text.toString(),
            )
        }
    }

    fun togglePasswordVisibility() {
        _registerState.update {
            it.copy(isPasswordVisible = !registerState.value.isPasswordVisible)
        }
    }

    fun validateRegistration() {
        val usernameResult = validateUsername.validate(registerState.value.username)
        val emailResult = validateEmail.validate(registerState.value.email)
        val passwordResult = validatePassword.validate(registerState.value.password)

        val validationResults = listOf(usernameResult, emailResult, passwordResult)
        val hasError = validationResults.any { it != ValidationResult.SUCCESSFUL }

        if (hasError) {
            _registerState.update { it ->
                it.copy(errorMessage = resolveErrorMessage(validationResults.first { it != ValidationResult.SUCCESSFUL }))
            }
            viewModelScope.launch {
                eventChannel.send(RegisterEvent.ValidationFailed(registerState.value.errorMessage))
            }
        } else {
            viewModelScope.launch {
                eventChannel.send(RegisterEvent.ValidationSuccess)
            }
        }
    }

    fun register() {
        viewModelScope.launch {
            try {
                val response = authRepository.register(registerState.value.username!!, registerState.value.email!!, registerState.value.password!!)
                eventChannel.send(RegisterEvent.RegistrationSucceeded)
            } catch (e: Exception) {
                eventChannel.send(RegisterEvent.RegistrationFailed(UiText.DynamicString(e.message.toString())))
            }
        }
    }

    private fun resolveErrorMessage(validationResult: ValidationResult): UiText? {
        return when (validationResult) {
            ValidationResult.EMAIL_BLANK -> UiText.StringResource(R.string.validation_error_email_blank)
            ValidationResult.EMAIL_INVALID -> UiText.StringResource(R.string.validation_error_email_invalid)
            ValidationResult.PASSWORD_TOO_SHORT -> UiText.StringResource(R.string.validation_error_password_length)
            ValidationResult.PASSWORD_BLANK -> UiText.StringResource(R.string.validation_error_password_empty)
            ValidationResult.PASSWORD_NO_LOWERCASE -> UiText.StringResource(R.string.validation_error_password_no_lowercase)
            ValidationResult.PASSWORD_NO_UPPERCASE -> UiText.StringResource(R.string.validation_error_password_no_uppercase)
            ValidationResult.PASSWORD_NO_NUMBER -> UiText.StringResource(R.string.validation_error_password_no_number)
            ValidationResult.USERNAME_INVALID -> UiText.StringResource(R.string.validation_error_username_invalid_length)
            ValidationResult.SUCCESSFUL -> null
        }
    }
}