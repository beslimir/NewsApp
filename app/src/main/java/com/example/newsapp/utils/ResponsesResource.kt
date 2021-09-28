package com.example.newsapp.utils

sealed class ResponsesResource<T>(
    val body: T? = null,
    val msg: String? = null
) {

    class SuccessResponse<T>(body: T): ResponsesResource<T>(body)
    class ErrorResponse<T>(msg: String, body: T? = null): ResponsesResource<T>(body, msg)
    class Loading<T>: ResponsesResource<T>()

}