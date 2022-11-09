package com.norvellium.tasky.core.web.interceptor

import com.norvellium.tasky.preferences.TokenPreferences
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(val tokenPreferences: TokenPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${tokenPreferences.readToken()}")
                .build()
        )
    }
}