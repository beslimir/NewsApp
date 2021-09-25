package com.example.newsapp

import androidx.room.Entity

data class NewsResponse(
    val nextPage: Int,
    val results: List<Result>,
    val status: String,
    val totalResults: Int
)