package com.exploring.source.repository

import com.exploring.source.model.ActivityResponse
import com.exploring.source.webservice.ApiClient
import com.exploring.source.webservice.ResponseHttpPattern
import com.exploring.source.webservice.api.fetchActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ActivityRepository(private val apiClient: ApiClient) {
    /**
     * This function is an example of how you can do a simple request
     */
    fun requireActivity(): Flow<ResponseHttpPattern<ActivityResponse>> = flow {
        val result = apiClient.fetchActivity(hashMapOf("participants" to "1")) // Always alone
        result?.let { emit(it) }
    }
}