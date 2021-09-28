package com.example.newsapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.api.NewsResponse
import com.example.newsapp.utils.Constants.Companion.COUNTRY_XY
import com.example.newsapp.utils.ResponsesResource
import kotlinx.coroutines.launch
import retrofit2.Response


class ArticleViewModel(val articleRepository: ArticleRepository): ViewModel() {

    val latestNews: MutableLiveData<ResponsesResource<NewsResponse>> = MutableLiveData()
    val searchNews: MutableLiveData<ResponsesResource<NewsResponse>> = MutableLiveData()

    init {
        getLatestNews(COUNTRY_XY)
    }

    /* Latest news */
    fun getLatestNews(country: String) = viewModelScope.launch {
        latestNews.postValue(ResponsesResource.Loading())
        val response = articleRepository.getLatestNews(country)
        latestNews.postValue(handleLatestNewsResponse(response))
    }

    private fun handleLatestNewsResponse(response: Response<NewsResponse>): ResponsesResource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return ResponsesResource.SuccessResponse(it)
            }
        }

        return ResponsesResource.ErrorResponse(response.message())
    }

    /* Search news */
    fun getSearchNews(query: String) = viewModelScope.launch {
        searchNews.postValue(ResponsesResource.Loading())
        val response = articleRepository.getSearchNews(query)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): ResponsesResource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return ResponsesResource.SuccessResponse(it)
            }
        }

        return ResponsesResource.ErrorResponse(response.message())
    }


}