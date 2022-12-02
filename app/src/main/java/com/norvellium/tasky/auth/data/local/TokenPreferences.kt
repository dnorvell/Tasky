package com.norvellium.tasky.auth.data.local

interface TokenPreferences {
    fun readToken(): String?
    fun writeToken(token: String): Boolean
    fun clearToken(): Boolean
}
