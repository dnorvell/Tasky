package com.norvellium.tasky.core.web

import com.norvellium.tasky.BuildConfig
import com.norvellium.tasky.core.web.response.NetworkResponse
import com.norvellium.tasky.core.web.response.ResponseError
import com.norvellium.tasky.auth.data.remote.LoginBody
import com.norvellium.tasky.auth.data.remote.LoginResponse
import com.norvellium.tasky.auth.data.remote.RegistrationBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TaskyApi {

    companion object {
        const val BASE_URL = "https://tasky.pl-coding.com/"
        const val API_KEY = BuildConfig.API_KEY
    }

    @POST("register")
    suspend fun register(@Body registrationDetails: RegistrationBody): NetworkResponse<Void, ResponseError>

    @POST("login")
    suspend fun login(@Body credentials: LoginBody): NetworkResponse<LoginResponse, ResponseError>

    @GET("authenticate")
    suspend fun authenticate(): NetworkResponse<Void, ResponseError>

    @GET("logout")
    suspend fun logout(): NetworkResponse<Void, ResponseError>

}