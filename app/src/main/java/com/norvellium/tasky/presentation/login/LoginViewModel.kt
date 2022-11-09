package com.norvellium.tasky.presentation.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norvellium.tasky.core.validation.ValidateEmail
import com.norvellium.tasky.preferences.TokenPreferences
import com.norvellium.tasky.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenPreferences: TokenPreferences,
    private val validateEmail: ValidateEmail,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val email = savedStateHandle.getStateFlow("email", "")
    private val password = savedStateHandle.getStateFlow("password", "")

    val state = combine(email, password) { email, password ->
        LoginState(
            email = email,
            password = password,
            isValidEmail = true, //TODO validator
            isValidPassword = false, //TODO validator
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LoginState())

    fun login() {

        viewModelScope.launch {

            try {
                val response = authRepository.login(state.value.email, state.value.password)
                tokenPreferences.writeToken(response.token)
                // TODO Save token on success and navigate
            }
            // TODO additional exception type
            catch (e: Exception) {
                // TODO handle errors
            }
        }
    }
}