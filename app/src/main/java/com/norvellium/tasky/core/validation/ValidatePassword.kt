package com.norvellium.tasky.core.validation

class ValidatePassword {
    fun validate(password: String): Boolean {
        return password.length >= 9
                && password.contains(Regex("[a-z]"))  // at least one lowercase
                && password.contains(Regex("[0-9]"))  // at least one number
                && password.contains(Regex("[A-Z]"))  // at least one uppercase
    }
}