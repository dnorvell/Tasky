package com.norvellium.tasky.core.validation

import androidx.core.util.PatternsCompat

class EmailPatternValidatorImpl: EmailPatternValidator {
    override fun isValidEmailPattern(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }
}