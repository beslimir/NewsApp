package com.example.newsapp.api

import com.example.newsapp.utils.Constants.Companion.API_KEY
import com.example.newsapp.utils.Constants.Companion.COUNTRY_XY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/*
* interface that provides access to the API
* */

interface NewsAPI {

    @GET("api/1/news")
    suspend fun getLatestNews(
        @Query("apikey")
        apiKey: String = API_KEY,
        @Query("country")
        countryCode: String = COUNTRY_XY
    ): Response<NewsResponse>

    @GET("api/1/news")
    suspend fun getSearchNews(
        @Query("apikey")
        apiKey: String = API_KEY,
        @Query("q")
        query: String
    ): Response<NewsResponse>


}