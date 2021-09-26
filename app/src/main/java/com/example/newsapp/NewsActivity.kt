package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class NewsActivity : AppCompatActivity() {

    lateinit var myViewModel: ArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myRepository = ArticleRepository(/*ArticleDatabase(this)*/)
        val myArticleViewModelFactory = ArticleViewModelFactory(myRepository)
        myViewModel = ViewModelProvider(this, myArticleViewModelFactory).get(ArticleViewModel::class.java)
        bottomNavigationMenu.setupWithNavController(navHostFragment.findNavController())


    }
}