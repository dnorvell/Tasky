package com.norvellium.tasky.preferences

import dagger.hilt.android.testing.HiltAndroidTest

import org.junit.Test

import com.google.common.truth.Truth.assertThat
import com.norvellium.tasky.TestCoroutineRule
import com.norvellium.tasky.auth.data.local.TokenPreferences
import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
@HiltAndroidTest
class TokenPreferencesTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Inject
    lateinit var tokenPrefs: TokenPreferences

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun it_can_read_and_write_token() = testCoroutineRule.runTest {
        assertThat(tokenPrefs.readToken()).isEqualTo(null)
        tokenPrefs.writeToken("test")
        assertThat(tokenPrefs.readToken()).isEqualTo("test")
    }

    @Test
    fun it_can_clear_token() = testCoroutineRule.runTest {
        tokenPrefs.clearToken()
        assertThat(tokenPrefs.readToken()).isEqualTo(null)
    }

    @After
    fun tearDown() {
        tokenPrefs.clearToken()
    }
}