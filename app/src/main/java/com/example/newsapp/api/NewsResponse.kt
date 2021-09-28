package com.example.newsapp.api

import com.example.newsapp.db.Article

data class NewsResponse(
    val nextPage: Int,
    val results: List<Article>,
    val status: String,
    val totalResults: Int
)