package com.norvellium.tasky.presentation.login

sealed interface LoginEvent {
    data class ValidationFailed(val message: String?) : LoginEvent
    object ValidationSuccess : LoginEvent
}