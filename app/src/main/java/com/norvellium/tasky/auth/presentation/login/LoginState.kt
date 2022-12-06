package com.norvellium.tasky.auth.presentation.login

data class LoginState(
    val email: String? = null,
    val password: String? = null,
    val isValidEmail: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val errorMessage: String? = null,
)
