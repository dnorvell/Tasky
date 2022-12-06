package com.norvellium.tasky.auth.data.local

import com.norvellium.tasky.core.web.response.NetworkResponse
import com.norvellium.tasky.core.web.TaskyApi
import com.norvellium.tasky.auth.data.remote.LoginBody
import com.norvellium.tasky.auth.data.remote.LoginResponse
import com.norvellium.tasky.auth.data.remote.RegistrationBody
import java.io.IOException

class AuthRepositoryImpl(
    val api: TaskyApi
) : AuthRepository {
    override suspend fun login(email: String, password: String): LoginResponse? {
        val body = LoginBody(email, password)
        val response = api.login(body)
        return when (response) {
            is NetworkResponse.Success -> response.body
            is NetworkResponse.ApiError -> throw Exception(response.body.message)
            is NetworkResponse.NetworkError -> throw  Exception(response.error)
            is NetworkResponse.UnknownError -> throw IOException(response.error)
        }
    }

    override suspend fun register(fullName: String, email: String, password: String) {
        val body = RegistrationBody(fullName, email, password)
        val response = api.register(body)
        when (response) {
            is NetworkResponse.Success -> return
            is NetworkResponse.ApiError -> throw Exception(response.body.message)
            is NetworkResponse.NetworkError -> throw  Exception(response.error)
            is NetworkResponse.UnknownError -> throw IOException(response.error)
        }
    }

    override suspend fun authenticate(token: String): Boolean {
        return when(val response = api.authenticate()) {
            is NetworkResponse.Success -> true
            is NetworkResponse.ApiError -> throw Exception("$response.body.status $response.body.message")
            is NetworkResponse.NetworkError -> true // Assume they are authenticated if network error
            is NetworkResponse.UnknownError -> throw IOException(response.error)
        }
    }
}