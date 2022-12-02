package com.norvellium.tasky.auth.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norvellium.tasky.auth.data.local.TokenPreferences
import com.norvellium.tasky.auth.data.local.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tokenPreferences: TokenPreferences,
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _isCheckingAuth = MutableStateFlow(true)
    val isCheckingAuth = _isCheckingAuth.asStateFlow()

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    init {
        viewModelScope.launch {
            _isCheckingAuth.value = true
            tokenPreferences.readToken()?.let { token ->
                Log.v("test", "authenticating")
                _isAuthenticated.value = authRepository.authenticate(token)
                Log.v("test", "authenticated")
            }
            _isCheckingAuth.value = false
        }
    }

}