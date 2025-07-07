package io.github.msaggik.data.api.db.dto.films.converter

import androidx.room.TypeConverter

class Converter {

    @TypeConverter
    fun listToInt(data: String): List<Int> {
        return data.split("*").map { it.toInt() }
    }

    @TypeConverter
    fun intToList(list: List<Int>): String {
        return list.joinToString("*")
    }
}