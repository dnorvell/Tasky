package com.norvellium.tasky.core.validation

import org.junit.Test

import org.junit.Before
import com.google.common.truth.Truth.*

class ValidatePasswordTest {
    private lateinit var validatePassword: ValidatePassword

    @Before
    fun setup() {
        validatePassword = ValidatePassword()
    }

    @Test
    fun password_must_be_at_least_9_characters() {
        assertThat(validatePassword.validate("Test1") == ValidationResult.PASSWORD_TOO_SHORT).isTrue()
    }

    @Test
    fun password_must_have_upper_case() {
        assertThat(validatePassword.validate("test1asdf") == ValidationResult.PASSWORD_NO_UPPERCASE).isTrue()
    }

    @Test
    fun password_must_have_number() {
        assertThat(validatePassword.validate("Testasdfa") == ValidationResult.PASSWORD_NO_NUMBER).isTrue()
    }

    @Test
    fun proper_password_validates() {
        assertThat(validatePassword.validate("Test1asdf") == ValidationResult.SUCCESSFUL).isTrue()
    }
}