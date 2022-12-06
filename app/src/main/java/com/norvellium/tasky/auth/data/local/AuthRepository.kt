package com.norvellium.tasky.auth.data.local

import com.norvellium.tasky.auth.data.remote.LoginResponse

interface AuthRepository {
    suspend fun login(email: String, password: String): LoginResponse?
    suspend fun register(fullName: String, email: String, password: String)
    suspend fun authenticate(token: String): Boolean
    suspend fun logout()
}