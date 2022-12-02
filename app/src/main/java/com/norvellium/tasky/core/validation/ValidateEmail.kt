package com.norvellium.tasky.core.validation

import javax.inject.Inject

class ValidateEmail @Inject constructor(
    private val emailPatternValidator: EmailPatternValidatorImpl
) {

    fun validate(email: String?): ValidationResult {
        if (email.isNullOrBlank()) {
            return ValidationResult.EMAIL_BLANK
        }
        if (!emailPatternValidator.isValidEmailPattern(email)) {
            return ValidationResult.EMAIL_INVALID
        }
        return ValidationResult.SUCCESSFUL
    }
}