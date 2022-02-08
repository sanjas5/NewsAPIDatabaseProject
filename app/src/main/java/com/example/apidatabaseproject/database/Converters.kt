package com.example.apidatabaseproject.database

import androidx.room.TypeConverter
import com.example.apidatabaseproject.models.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String{
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}