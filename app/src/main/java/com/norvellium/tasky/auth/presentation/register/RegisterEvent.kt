package com.norvellium.tasky.auth.presentation.register

sealed interface RegisterEvent {
    data class ValidationFailed(val message: String?) : RegisterEvent
    object ValidationSuccess : RegisterEvent
    object RegistrationSucceeded : RegisterEvent
    data class RegistrationFailed(val message: String?) : RegisterEvent
}