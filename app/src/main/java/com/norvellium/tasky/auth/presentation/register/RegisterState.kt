package com.norvellium.tasky.auth.presentation.register

import com.norvellium.tasky.core.util.UiText

data class RegisterState(
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val isValidUsername: Boolean = false,
    val isValidEmail: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val errorMessage: UiText? = null,
)
