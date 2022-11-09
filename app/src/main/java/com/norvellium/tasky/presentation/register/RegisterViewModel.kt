package com.norvellium.tasky.presentation.register

import androidx.lifecycle.ViewModel
import com.norvellium.tasky.core.validation.ValidateEmail
import com.norvellium.tasky.core.validation.ValidatePassword
import com.norvellium.tasky.core.validation.ValidateUsername
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validateUsername: ValidateUsername
) : ViewModel() {
    // TODO: Implement the ViewModel
}