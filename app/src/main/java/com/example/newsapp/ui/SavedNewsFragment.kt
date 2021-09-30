package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.ArticleViewModel
import com.example.newsapp.ArticlesAdapter
import com.example.newsapp.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_latest_news.*

class SavedNewsFragment : Fragment(R.layout.fragment_latest_news) {

    lateinit var myViewModel: ArticleViewModel
    lateinit var myAdapter: ArticlesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myViewModel = (activity as NewsActivity).myViewModel
        myAdapter = ArticlesAdapter()
        rvLatestNews.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        myAdapter.setOnItemClickListener { myArticle ->
            val bundle = Bundle().apply {
                putSerializable("article", myArticle)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )

        }

        myViewModel.getSavedArticles().observe(viewLifecycleOwner, Observer { myArticles ->
            myAdapter.differ.submitList(myArticles)
        })

        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val article = myAdapter.differ.currentList[viewHolder.adapterPosition]
                myViewModel.deleteArticle(article)
                Snackbar.make(view, "You deleted an article", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        myViewModel.upsertArticle(article)
                    }.show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvLatestNews)
        }


    }

}