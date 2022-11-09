package com.norvellium.tasky.core.validation

import android.content.Context
import org.junit.Test

import com.google.common.truth.Truth.*
import org.junit.Before
import org.mockito.Mockito.mock

class ValidateEmailTest {
    private lateinit var validateEmail: ValidateEmail

    @Before
    fun setup() {
        validateEmail = ValidateEmail(mock(Context::class.java))
    }

    @Test
    fun proper_email_validates() {
        assertThat(validateEmail.validate("drew@test.com").successful).isTrue()
    }

    @Test
    fun improper_email_doesnt_validate() {
        assertThat(validateEmail.validate("drew@testcom").successful).isFalse()
    }
}