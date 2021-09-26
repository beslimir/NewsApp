package com.example.newsapp

data class NewsResponse(
    val nextPage: Int,
    val results: List<Article>,
    val status: String,
    val totalResults: Int
)