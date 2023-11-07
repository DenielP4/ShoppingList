package com.example.shoppinglist.data

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters {

    @TypeConverter
    fun fromListToString(list: List<AddItem>?): String? {
        if (list == null) return null
        val gson = Gson()
        return gson.toJson(list)    }

    @TypeConverter
    fun fromStringToList(string: String?): List<AddItem>? {
        if (string == null) return null
        val gson = Gson()
        val type = object : TypeToken<List<AddItem>>() {}.type
        return gson.fromJson(string, type)
    }
}