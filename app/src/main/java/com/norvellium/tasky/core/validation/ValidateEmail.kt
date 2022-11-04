package com.norvellium.tasky.core.validation

import androidx.core.util.PatternsCompat

class ValidateEmail {
    fun validate(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }
}