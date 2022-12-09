package com.norvellium.tasky.auth.presentation.login

import com.norvellium.tasky.core.util.UiText

data class LoginState(
    val email: String? = null,
    val password: String? = null,
    val isValidEmail: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val errorMessage: UiText? = null,
)
