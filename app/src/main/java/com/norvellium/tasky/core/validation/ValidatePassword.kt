package com.norvellium.tasky.core.validation

import android.content.Context
import com.norvellium.tasky.R

class ValidatePassword(context: Context) {

    private val resources = context.resources

    fun validate(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(false, resources?.getString(R.string.validation_error_password_empty))
        }
        // more than 8 characters
        if (password.length < 8) {
            return ValidationResult(false, resources?.getString(R.string.validation_error_password_length))
        }
        // at least one lowercase
        if (!password.contains(Regex("[a-z]"))) {
            return ValidationResult(false, resources?.getString(R.string.validation_error_password_no_lowercase))
        }
        // at least one number
        if (!password.contains(Regex("[0-9]"))) {
            return ValidationResult(false, resources?.getString(R.string.validation_error_password_no_number))
        }
        // at least one uppercase
        if (!password.contains(Regex("[A-Z]"))) {
            return ValidationResult(false, resources?.getString(R.string.validation_error_password_no_uppercase))
        }
        return ValidationResult(true)
    }
}