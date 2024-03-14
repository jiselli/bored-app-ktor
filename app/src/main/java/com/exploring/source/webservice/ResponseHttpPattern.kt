package com.exploring.source.webservice

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseHttpPattern<T : Any?>(
    var success: Boolean = true,
    @SerialName("statusCode")
    val statusCode: String,
    @SerialName("statusMessage")
    val statusMessage: String,
    @SerialName("errorMessage")
    val errorMessage: List<String>? = null,
    @SerialName("data")
    val responseBody: T? = null
)
