package com.norvellium.tasky.web

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseError(
    val status: String,
    val message: String
)
