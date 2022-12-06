package com.norvellium.tasky.agenda.presentation.agenda

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.norvellium.tasky.auth.data.local.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    application: Application,
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

}