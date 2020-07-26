package com.andyho.sampleapplication.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringArrayConverter {

    companion object {
        @TypeConverter
        @JvmStatic
        fun fromStringString(value: String?):ArrayList<String>? {
            if (value != null) {
                val listType = object : TypeToken<ArrayList<String>>() {}.getType()
                return Gson().fromJson(value, listType)
            }
            return null
        }

        @TypeConverter
        @JvmStatic
        fun fromArrayListString(list: ArrayList<String>?): String? {
            if (list != null) {
                val gson = Gson()
                return gson.toJson(list)
            }

            return null
        }
    }
}