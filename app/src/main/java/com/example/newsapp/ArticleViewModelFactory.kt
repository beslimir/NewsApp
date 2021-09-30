package com.example.newsapp

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ArticleViewModelFactory(val app: Application, val articleRepository: ArticleRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleViewModel(app, articleRepository) as T
    }
}