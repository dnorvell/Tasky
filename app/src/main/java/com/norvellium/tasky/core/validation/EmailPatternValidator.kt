package com.norvellium.tasky.core.validation

interface EmailPatternValidator {
    fun isValidEmailPattern(email: String): Boolean
}