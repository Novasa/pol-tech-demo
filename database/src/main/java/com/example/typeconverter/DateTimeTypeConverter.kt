package com.example.typeconverter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.utility.date.injection.FormatterDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@ProvidedTypeConverter
class DateTimeTypeConverter @Inject constructor(
    @FormatterDateTime private val formatter: DateTimeFormatter
) {

    @TypeConverter
    fun stringToDate(value: String?): ZonedDateTime? {
        return value?.let { ZonedDateTime.parse(it, formatter) }
    }

    @TypeConverter
    fun dateToString(date: ZonedDateTime?): String? {
        return date?.let { formatter.format(it) }
    }
}
