package com.norvellium.tasky.preferences

interface TokenPreferences {
    fun readToken(): String?
    fun writeToken(token: String): Boolean
    fun clearToken()
}
