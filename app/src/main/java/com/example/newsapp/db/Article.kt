package com.example.newsapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "articles"
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val content: Any?,
    val creator: List<String?>?, //list of strings - publishers
    val description: String?,
    val image_url: String?,
    val keywords: Any?,
    val link: String,
    val pubDate: String?,
    val source_id: String?,
    val title: String?,
    val video_url: Any?
): Serializable