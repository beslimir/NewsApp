package com.example.newsapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response


class ArticleViewModel(val articleRepository: ArticleRepository): ViewModel() {

    val latestNews: MutableLiveData<ResponsesResource<NewsResponse>> = MutableLiveData()

    init {
        getLatestNews("us")
    }

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


}