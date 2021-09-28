package com.example.newsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.ArticleViewModel
import com.example.newsapp.ArticlesAdapter
import com.example.newsapp.R
import com.example.newsapp.utils.Constants
import com.example.newsapp.utils.Constants.Companion.DELAY_TIME
import com.example.newsapp.utils.ResponsesResource
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    lateinit var myViewModel: ArticleViewModel
    lateinit var myAdapter: ArticlesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myViewModel = (activity as NewsActivity).myViewModel

        myAdapter = ArticlesAdapter()
        rvSearchNews.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        var job: Job? = null
        etSearch.addTextChangedListener {
            job?.cancel() //if we type something, cancel the current job
            job = MainScope().launch {
                delay(DELAY_TIME) //delay to avoid too many api calls
                etSearch?.let {
                    if (etSearch.text.toString().isNotEmpty()) {
                        myViewModel.getSearchNews(etSearch.text.toString())
                    }
                }
            }

        }

        myViewModel.searchNews.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResponsesResource.SuccessResponse -> {
                    hideProgressBar()
                    it.body?.let { newsResponse ->
                        myAdapter.differ.submitList(newsResponse.results)
                    }
                }
                is ResponsesResource.ErrorResponse -> {
                    hideProgressBar()
                    it.msg?.let { message ->
                        Log.d(Constants.LOG_TAG, "Error: $message")
                    }
                }
                is ResponsesResource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        pbSearch.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        pbSearch.visibility = View.VISIBLE
    }
}