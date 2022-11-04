package com.norvellium.tasky.core.validation

class ValidateUsername {
    fun validate(username: String): Boolean {
        return username.length in 4..50 // between 4 and 50 characters
    }
}