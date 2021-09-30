package com.example.newsapp.ui

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsapp.ArticleViewModel
import com.example.newsapp.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var myViewModel: ArticleViewModel
    val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myViewModel = (activity as NewsActivity).myViewModel
        val myArticle = args.article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(args.article.link)
        }

        fab.setOnClickListener {
            myViewModel.upsertArticle(myArticle)
            Snackbar.make(view, "Article successfully saved!", Snackbar.LENGTH_SHORT).show()
        }
    }
}