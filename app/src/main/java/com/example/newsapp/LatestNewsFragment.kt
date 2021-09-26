package com.example.newsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.Constants.Companion.LOG_TAG
import kotlinx.android.synthetic.main.fragment_latest_news.*

class LatestNewsFragment : Fragment(R.layout.fragment_latest_news) {

    lateinit var myViewModel: ArticleViewModel
    lateinit var myAdapter: ArticlesAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        myViewModel = (activity as NewsActivity).myViewModel

        myAdapter = ArticlesAdapter()
        rvLatestNews.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        myViewModel.latestNews.observe(viewLifecycleOwner, Observer {
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
                        Log.d(LOG_TAG, "Error: $message")
                    }
                }
                is ResponsesResource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        pbProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        pbProgressBar.visibility = View.VISIBLE
    }

}