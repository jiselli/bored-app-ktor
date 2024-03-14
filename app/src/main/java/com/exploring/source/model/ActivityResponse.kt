package com.exploring.source.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityResponse(
    @SerialName("activity")
    val activity: String,
    @SerialName("accessibility")
    val accessibility: Double,
    @SerialName("type")
    val type: String,
    @SerialName("participants")
    val participants: Int,
    @SerialName("price")
    val price: Double,
    @SerialName("link")
    val link: String,
    @SerialName("key")
    val key: String,
)