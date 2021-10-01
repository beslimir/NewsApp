package com.example.newsapp.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {

    @TypeConverter
    fun fromListToString(list: List<String>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToList(string: String?): List<String>? {
        val listType = object: TypeToken<List<String?>?>(){}.type
        return Gson().fromJson(string, listType)
    }

    @TypeConverter
    fun fromAnyToString(any: Any?): String? {
        return any?.toString()
    }

    @TypeConverter
    fun fromStringToAny(string: String?): Any? {
        val anyType = object: TypeToken<Any?>(){}.type
        return Gson().fromJson(string, anyType)
    }

}