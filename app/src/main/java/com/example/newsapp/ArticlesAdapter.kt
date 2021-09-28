package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.db.Article
import kotlinx.android.synthetic.main.article_item.view.*

class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        return ArticlesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val myArticle = differ.currentList[position]
        holder.itemView.apply {
            tvArticleTitle.text = myArticle.title
            tvArticleDescription.text = myArticle.description
            //use Glide for images
            Glide.with(this)
                .load(myArticle.image_url)
                .error(resources.getDrawable(R.drawable.ic_launcher_background))
                .into(ivArticlePicture)

            setOnClickListener {
                onItemClickListener?.let {
                    it(myArticle)
                }
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    //create on click listener
    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }



    //use instead of notifyOnSetDataChanges() because differ refreshes only views that really changed
    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.link == newItem.link //because no id provided by the server
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    //compare lists
    val differ = AsyncListDiffer(this, differCallback)

    inner class ArticlesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}