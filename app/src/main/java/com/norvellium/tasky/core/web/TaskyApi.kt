package com.norvellium.tasky.core.web

import com.norvellium.tasky.BuildConfig
import com.norvellium.tasky.data.remote.LoginResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface TaskyApi {

    companion object {
        const val BASE_URL = "https://tasky.pl-coding.com/"
        const val API_KEY = BuildConfig.API_KEY
    }

    @POST("register")
    suspend fun register(): Response<Void>

    @POST("login")
    suspend fun login(): Response<LoginResponse>

    @GET("authenticate")
    suspend fun authenticate()

    @GET("logout")
    suspend fun logout()

}