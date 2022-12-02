package com.norvellium.tasky.core.validation

import android.content.Context
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock


class ValidateUsernameTest {
    private lateinit var validateUsername: ValidateUsername


    @Before
    fun setup() {
        validateUsername = ValidateUsername()
    }

    @Test
    fun proper_user_name_validates() {
        Truth.assertThat(validateUsername.validate("drewn") == ValidationResult.SUCCESSFUL).isTrue()
    }

    @Test
    fun username_must_be_at_least_4_characters() {
        Truth.assertThat(validateUsername.validate("dre") == ValidationResult.USERNAME_INVALID).isTrue()
    }

    @Test
    fun username_cant_be_longer_than_50_characters() {
        Truth.assertThat(validateUsername.validate("thisisareallylongusernamethatissolongthatitisoverfiftycharacters") == ValidationResult.USERNAME_INVALID)
            .isTrue()
    }
}