package com.example.newsapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var myViewModel: ArticleViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel = (activity as NewsActivity).myViewModel
    }
}