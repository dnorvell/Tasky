package com.norvellium.tasky.core.validation

import android.content.Context
import org.junit.Test

import org.junit.Before
import com.google.common.truth.Truth.*
import org.mockito.Mockito.mock

class ValidatePasswordTest {
    private lateinit var validatePassword: ValidatePassword

    @Before
    fun setup() {
        validatePassword = ValidatePassword(mock(Context::class.java))
    }

    @Test
    fun password_must_be_at_least_9_characters() {
        assertThat(validatePassword.validate("Test1").successful).isFalse()
    }

    @Test
    fun password_must_have_upper_case() {
        assertThat(validatePassword.validate("test1asdf").successful).isFalse()
    }

    @Test
    fun password_must_have_number() {
        assertThat(validatePassword.validate("Testasdfa").successful).isFalse()
    }

    @Test
    fun proper_password_validates() {
        assertThat(validatePassword.validate("Test1asdf").successful).isTrue()
    }
}