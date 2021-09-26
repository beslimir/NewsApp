package com.example.newsapp

import com.example.newsapp.Constants.Companion.API_KEY

/*
* Use the api from this repository, without passing it to the constructor.
* Just for Room db this constructor is needed.
* */

class ArticleRepository(/*val db: ArticleDatabase*/) {

    suspend fun getLatestNews(country: String) = RetrofitInstance.api.getLatestNews(API_KEY, country)

}