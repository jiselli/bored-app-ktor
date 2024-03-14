package com.exploring.source.webservice

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json


@OptIn(InternalAPI::class)
class ApiClient(httpClientEngine: HttpClientEngine) {
//  HttpClientEngine will help us implement unit tests: https://ktor.io/docs/http-client-testing.html#test-client

    val baseUrl = "https://www.boredapi.com"

    val client = HttpClient(httpClientEngine) {
        install(HttpTimeout) {
            requestTimeoutMillis = 20000
            socketTimeoutMillis = 20000
            connectTimeoutMillis = 20000
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(DefaultRequest) {
            headers.append("Content-Type", "application/json")
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }


    suspend inline fun <reified T : Any> get(
        endpoint: String,
        parameters: HashMap<String, String> = hashMapOf(),
    ): ResponseHttpPattern<T>? {
        val url = baseUrl + endpoint
        return try {
            val response = client.get {
                url(url)
                parameters.forEach { parameter ->
                    parameter(parameter.key, parameter.value)
                }
            }
            parseSuccessResponse<T>(response)
        } catch (exception: ClientRequestException) {
            parseErrorResponse<T>(exception.response)
        } catch (exception: Exception) {
            defaultResponseWithoutConnection<T>(exception)
        }
    }

    suspend inline fun <reified T : Any> post(
        endpoint: String,
        parameters: HashMap<String, String> = hashMapOf(),
        bodyRequest: Any? = null,
    ): ResponseHttpPattern<T>? {
        val url = baseUrl + endpoint
        return try {
            val response = client.post {
                url(url)
                parameters.forEach { parameter ->
                    parameter(parameter.key, parameter.value)
                }
                bodyRequest?.let { validBody ->
                    body = validBody
                }
            }
            return parseSuccessResponse<T>(response)
        } catch (exception: ClientRequestException) {
            parseErrorResponse<T>(exception.response)
        } catch (exception: Exception) {
            defaultResponseWithoutConnection<T>(exception)
        }
    }
}