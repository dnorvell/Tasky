package com.norvellium.tasky.ui.login

data class LoginState(
    var email: String = "",
    var password: String = "",
    var isValidEmail: Boolean = true,
    var isValidPassword: Boolean = true
) {
    fun loginEnabled(): Boolean {
        return isValidEmail && isValidPassword
    }
}
