package com.norvellium.tasky.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class TokenPreferencesImpl(val context: Context): TokenPreferences {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = name)
    private val tokenKey = stringPreferencesKey(key)

    override suspend fun readToken(): String? {
        val tokenKey = stringPreferencesKey(key)
        val prefs = context.dataStore.data.first()
        return prefs[tokenKey]
    }

    override suspend fun writeToken(token: String) {
        context.dataStore.edit { settings ->
            settings[tokenKey] = token
        }
    }

    override suspend fun clearToken() {
        context.dataStore.edit { settings ->
            settings.remove(tokenKey)
        }
    }

    companion object {
        private const val name = "TOKEN_PREFS"
        private const val key = "TOKEN"
    }
}
