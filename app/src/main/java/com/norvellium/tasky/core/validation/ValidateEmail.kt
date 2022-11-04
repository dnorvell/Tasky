package com.norvellium.tasky.core.validation

class ValidateEmail {
    fun validate(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}