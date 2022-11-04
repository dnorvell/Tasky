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
        assertThat(validatePassword.validate("Test1")).isFalse()
    }

    @Test
    fun password_must_have_upper_case() {
        assertThat(validatePassword.validate("test1asdf")).isFalse()
    }

    @Test
    fun password_must_have_number() {
        assertThat(validatePassword.validate("Testasdfa")).isFalse()
    }

    @Test
    fun proper_password_validates() {
        assertThat(validatePassword.validate("Test1asdf")).isTrue()
    }
}