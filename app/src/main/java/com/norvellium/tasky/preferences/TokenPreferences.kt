package com.norvellium.tasky.preferences

interface TokenPreferences {
    suspend fun readToken(): String?
    suspend fun writeToken(token: String)
    suspend fun clearToken()
}
