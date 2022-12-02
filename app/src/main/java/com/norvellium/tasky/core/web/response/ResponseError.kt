package com.norvellium.tasky.core.web.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseError(
    val message: String
)
