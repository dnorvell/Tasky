package com.norvellium.tasky.auth.presentation.login

sealed interface LoginEvent {
    data class ValidationFailed(val message: String?) : LoginEvent
    object ValidationSuccess : LoginEvent
    object LoginSucceeded : LoginEvent
    data class LoginFailed(val message: String?) : LoginEvent
}