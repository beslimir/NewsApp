package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.newsapp.ArticleViewModel
import com.example.newsapp.R

class SavedNewsFragment : Fragment(R.layout.fragment_latest_news) {

    lateinit var myViewModel: ArticleViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel = (activity as NewsActivity).myViewModel
    }

}