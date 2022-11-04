package com.norvellium.tasky.core.validation

import org.junit.Test

import com.google.common.truth.Truth.*
import org.junit.Before

class ValidateEmailTest {
    private lateinit var validateEmail: ValidateEmail

    @Before
    fun setup() {
        validateEmail = ValidateEmail()
    }

    @Test
    fun proper_email_validates() {
        assertThat(validateEmail.validate("drew@test.com")).isTrue()
    }

    @Test
    fun improper_email_doesnt_validate() {
        assertThat(validateEmail.validate("drew@testcom")).isFalse()
    }
}