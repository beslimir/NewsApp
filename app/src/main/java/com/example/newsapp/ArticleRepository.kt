package com.example.newsapp

import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.db.Article
import com.example.newsapp.utils.Constants.Companion.API_KEY
import com.example.newsapp.db.ArticleDatabase

/*
* Use the api from this repository, without passing it to the constructor.
* Just for Room db this constructor is needed.
* */

class ArticleRepository(val db: ArticleDatabase) {

    suspend fun getLatestNews(country: String) = RetrofitInstance.api.getLatestNews(API_KEY, country)

    suspend fun getSearchNews(query: String) = RetrofitInstance.api.getSearchNews(API_KEY, query)

    suspend fun upsertArticle(article: Article) = db.getArticleDao().upsertArticle(article)

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)

    fun getSavedArticles() = db.getArticleDao().getAllArticles()

}