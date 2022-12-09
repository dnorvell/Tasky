package com.norvellium.tasky.auth.presentation.register

import com.norvellium.tasky.core.util.UiText

sealed interface RegisterEvent {
    data class ValidationFailed(val message: UiText?) : RegisterEvent
    object ValidationSuccess : RegisterEvent
    object RegistrationSucceeded : RegisterEvent
    data class RegistrationFailed(val message: UiText?) : RegisterEvent
}