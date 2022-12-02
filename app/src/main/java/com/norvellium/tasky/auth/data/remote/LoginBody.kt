package com.norvellium.tasky.auth.data.remote

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class LoginBody(
    val email: String,
    val password: String
) : Serializable
