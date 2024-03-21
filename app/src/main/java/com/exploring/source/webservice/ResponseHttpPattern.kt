package com.exploring.source.webservice

import kotlinx.serialization.Serializable

@Serializable
data class ResponseHttpPattern<T : Any?>(
    var success: Boolean = true,
    val statusCode: String,
    val statusMessage: String,
    val errorMessage: List<String>? = null,
    val responseBody: T? = null
)
