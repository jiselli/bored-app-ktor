package com.exploring.source.webservice.api

import com.exploring.source.model.ActivityResponse
import com.exploring.source.webservice.ApiClient
import com.exploring.source.webservice.ResponseHttpPattern

suspend fun ApiClient.fetchActivity(parameters: HashMap<String, String> = hashMapOf()): ResponseHttpPattern<ActivityResponse>? {
    return get<ActivityResponse>("/api/activity", parameters)
}

// Here Here you can implement other requests with any body type....