package com.example.newsapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDAO {

    @Query("SELECT * from articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertArticle(article: Article)

    @Delete
    suspend fun deleteArticle(article: Article)


}