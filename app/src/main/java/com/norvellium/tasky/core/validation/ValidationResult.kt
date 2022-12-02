package com.norvellium.tasky.core.validation

enum class ValidationResult {
    EMAIL_BLANK,
    EMAIL_INVALID,
    PASSWORD_TOO_SHORT,
    PASSWORD_BLANK,
    PASSWORD_NO_LOWERCASE,
    PASSWORD_NO_UPPERCASE,
    PASSWORD_NO_NUMBER,
    USERNAME_INVALID,
    SUCCESSFUL
}