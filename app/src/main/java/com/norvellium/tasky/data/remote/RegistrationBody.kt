package com.norvellium.tasky.data.remote

data class RegistrationBody(
    val fullName: String,
    val email: String,
    val password: String
)
