package com.norvellium.tasky.auth.presentation.login

import com.norvellium.tasky.core.util.UiText

sealed interface LoginEvent {
    data class ValidationFailed(val message: UiText?) : LoginEvent
    object ValidationSuccess : LoginEvent
    object LoginSucceeded : LoginEvent
    data class LoginFailed(val message: UiText?) : LoginEvent
}