package com.example.newsapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "articles"
)
data class Result(
    val content: Any,
    val creator: List<String>, //list of strings - publishers
    val description: String,
    val image_url: String,
    val keywords: Any,
    @PrimaryKey
    val link: String,
    val pubDate: String,
    val source_id: String,
    val title: String,
    val video_url: Any
)