package com.example.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.ArticleRepository
import com.example.newsapp.ArticleViewModel
import com.example.newsapp.ArticleViewModelFactory
import com.example.newsapp.R
import com.example.newsapp.db.ArticleDatabase
import kotlinx.android.synthetic.main.activity_main.*

class NewsActivity : AppCompatActivity() {

    lateinit var myViewModel: ArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myRepository = ArticleRepository(ArticleDatabase(this))
        val myArticleViewModelFactory = ArticleViewModelFactory(application, myRepository)
        myViewModel = ViewModelProvider(this, myArticleViewModelFactory).get(ArticleViewModel::class.java)
        bottomNavigationMenu.setupWithNavController(navHostFragment.findNavController())


    }
}