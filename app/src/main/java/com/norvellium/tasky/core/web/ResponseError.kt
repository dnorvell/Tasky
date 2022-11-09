package com.norvellium.tasky.core.web

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseError(
    val message: String
)
