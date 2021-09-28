package com.example.newsapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.newsapp.ArticleViewModel
import com.example.newsapp.R

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var myViewModel: ArticleViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel = (activity as NewsActivity).myViewModel
    }
}