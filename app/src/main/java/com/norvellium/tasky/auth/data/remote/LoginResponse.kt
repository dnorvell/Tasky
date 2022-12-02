package com.norvellium.tasky.auth.data.remote

data class LoginResponse(
    val token: String,
    val userId: String,
    val fullName: String
)
