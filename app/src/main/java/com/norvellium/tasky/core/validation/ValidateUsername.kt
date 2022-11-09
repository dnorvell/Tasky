package com.norvellium.tasky.core.validation

import android.content.Context
import com.norvellium.tasky.R

class ValidateUsername(context: Context) {

    private val resources = context.resources

    fun validate(username: String): ValidationResult {
        // between 4 and 50 characters
        if (username.length !in 4..50) {
            return ValidationResult(
                false,
                resources.getString(R.string.validation_error_username_invalid_length)
            )
        }
        return ValidationResult(true)
    }
}