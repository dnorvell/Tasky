package com.norvellium.tasky.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isValidEmail: Boolean = true,
    val isValidPassword: Boolean = true,
    val errorMessage: String = "",
)
