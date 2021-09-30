package com.example.newsapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.api.NewsResponse
import com.example.newsapp.db.Article
import com.example.newsapp.utils.Constants.Companion.COUNTRY_XY
import com.example.newsapp.utils.ResponsesResource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


class ArticleViewModel(app: Application, val articleRepository: ArticleRepository) :
    AndroidViewModel(app) {

    val latestNews: MutableLiveData<ResponsesResource<NewsResponse>> = MutableLiveData()
    val searchNews: MutableLiveData<ResponsesResource<NewsResponse>> = MutableLiveData()

    init {
        getLatestNews(COUNTRY_XY)
    }

    /* Latest news */
    fun getLatestNews(country: String) = viewModelScope.launch {
        safeLatestNewsCall(country)
    }

    private fun handleLatestNewsResponse(response: Response<NewsResponse>): ResponsesResource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return ResponsesResource.SuccessResponse(it)
            }
        }

        return ResponsesResource.ErrorResponse(response.message())
    }

    private suspend fun safeLatestNewsCall(countryCode: String) {
        latestNews.postValue(ResponsesResource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = articleRepository.getLatestNews(countryCode)
                latestNews.postValue(handleLatestNewsResponse(response))
            } else {
                latestNews.postValue(ResponsesResource.ErrorResponse("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> latestNews.postValue(ResponsesResource.ErrorResponse("Network Failure"))
                else -> latestNews.postValue(ResponsesResource.ErrorResponse("Conversion Error"))
            }
        }
    }

    /* Search news */
    fun getSearchNews(query: String) = viewModelScope.launch {
        safeSearchNewsCall(query)
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): ResponsesResource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return ResponsesResource.SuccessResponse(it)
            }
        }

        return ResponsesResource.ErrorResponse(response.message())
    }

    private suspend fun safeSearchNewsCall(query: String) {
        searchNews.postValue(ResponsesResource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = articleRepository.getSearchNews(query)
                searchNews.postValue(handleSearchNewsResponse(response))
            } else {
                searchNews.postValue(ResponsesResource.ErrorResponse("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> searchNews.postValue(ResponsesResource.ErrorResponse("Network Failure"))
                else -> searchNews.postValue(ResponsesResource.ErrorResponse("Conversion Error"))
            }
        }
    }

    /* Saved news */
    fun upsertArticle(article: Article) = viewModelScope.launch {
        articleRepository.upsertArticle(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        articleRepository.deleteArticle(article)
    }

    fun getSavedArticles() = articleRepository.getSavedArticles()

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<NewsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }

        return false
    }


}