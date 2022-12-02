package com.norvellium.tasky.core.validation

import android.content.Context
import com.norvellium.tasky.R

class ValidateUsername() {

    fun validate(username: String): ValidationResult {
        // between 4 and 50 characters
        if (username.length !in 4..50) {
            return ValidationResult.USERNAME_INVALID
        }
        return ValidationResult.SUCCESSFUL
    }
}