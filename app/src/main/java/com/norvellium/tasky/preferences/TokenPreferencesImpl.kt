package com.norvellium.tasky.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class TokenPreferencesImpl(val context: Context): TokenPreferences {

    private var prefs: SharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)

    // TODO handle token encryption

    override fun readToken(): String? {
        return prefs.getString(key, null)
    }

    override fun writeToken(token: String): Boolean {
        val editor = prefs.edit()
        editor.putString(key, token)
        return editor.commit()
    }

    override fun clearToken(): Boolean {
        val editor = prefs.edit()
        editor.remove(key)
        return editor.commit()
    }

//    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = name)
//    private val tokenKey = stringPreferencesKey(key)
//
//    override suspend fun readToken(): String? {
//        val tokenKey = stringPreferencesKey(key)
//        val prefs = context.dataStore.data.first()
//        return prefs[tokenKey]
//    }
//
//    override suspend fun writeToken(token: String) {
//        context.dataStore.edit { settings ->
//            settings[tokenKey] = token
//        }
//    }
//
//    override suspend fun clearToken() {
//        context.dataStore.edit { settings ->
//            settings.remove(tokenKey)
//        }
//    }

    companion object {
        private const val name = "TOKEN_PREFS"
        private const val key = "TOKEN"
    }
}
