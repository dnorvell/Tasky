package com.norvellium.tasky.core.validation

import android.content.Context
import com.norvellium.tasky.R

class ValidatePassword() {

    fun validate(password: String?): ValidationResult {
        if (password.isNullOrBlank()) {
            return ValidationResult.PASSWORD_BLANK
        }
        if (password.length < 8) {
            return ValidationResult.PASSWORD_TOO_SHORT
        }
        if (!password.any { it.isLowerCase() }) {
            return ValidationResult.PASSWORD_NO_LOWERCASE
        }
        // at least one number
        if (!password.any { it.isDigit() }) {
            return ValidationResult.PASSWORD_NO_NUMBER
        }
        // at least one uppercase
        if (!password.any { it.isUpperCase() }) {
            return ValidationResult.PASSWORD_NO_UPPERCASE
        }
        return ValidationResult.SUCCESSFUL
    }
}