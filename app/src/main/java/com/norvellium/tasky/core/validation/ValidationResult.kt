package com.norvellium.tasky.core.validation

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null,
)