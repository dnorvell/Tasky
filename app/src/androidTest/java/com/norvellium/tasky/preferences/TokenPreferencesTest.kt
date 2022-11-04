package com.norvellium.tasky.preferences

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import com.google.common.truth.Truth.assertThat
import com.norvellium.tasky.TestCoroutineRule
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
    fun itCanReadandWriteToken() = testCoroutineRule.runTest {
        assertThat(tokenPrefs.readToken()).isEqualTo(null)
        tokenPrefs.writeToken("test")
        assertThat(tokenPrefs.readToken()).isEqualTo("test")
        tokenPrefs.clearToken()
        assertThat(tokenPrefs.readToken()).isEqualTo(null)
    }

}