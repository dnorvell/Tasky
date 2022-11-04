package com.norvellium.tasky.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norvellium.tasky.preferences.TokenPreferences
import com.norvellium.tasky.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tokenPreferences: TokenPreferences,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isCheckingAuth = MutableStateFlow(true)
    val isCheckingAuth = _isCheckingAuth.asStateFlow()

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    init {
        viewModelScope.launch {
            _isCheckingAuth.value = true
            val token = tokenPreferences.readToken()
//            authRepository.checkAuthentication()
            // TODO read token
            _isCheckingAuth.value = false
        }
    }

}