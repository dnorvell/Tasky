package com.norvellium.tasky.core.validation

import android.content.Context
import androidx.core.util.PatternsCompat
import com.norvellium.tasky.R

class ValidateEmail(context: Context) {

    private val resources = context.resources

    fun validate(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(false, resources.getString(R.string.validation_error_email_blank))
        }
        if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(false, resources?.getString(R.string.validation_error_email_invalid))
        }
        return ValidationResult(true)
    }
}