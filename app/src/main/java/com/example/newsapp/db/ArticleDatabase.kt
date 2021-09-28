package com.example.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.utils.TypeConverter


@Database(
    entities = [Article::class],
    version = 2
)
@TypeConverters(TypeConverter::class)
abstract class ArticleDatabase: RoomDatabase() {

    abstract fun getArticleDao(): ArticleDAO

    companion object {
        @Volatile //other threads can see when a thread changes this instance
        private var instance: ArticleDatabase? = null
        private val LOCK = Any()

        //this method runs every time we try to instantiate
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ArticleDatabase::class.java,
            "articleDatabase.db"
        ).build()

    }
}