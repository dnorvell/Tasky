package com.norvellium.tasky.auth.data.remote

data class RegistrationBody(
    val fullName: String,
    val email: String,
    val password: String
)
