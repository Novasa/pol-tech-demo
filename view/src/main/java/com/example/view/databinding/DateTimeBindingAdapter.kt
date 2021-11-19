package com.example.view.databinding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.utility.date.injection.FormatterDate
import com.example.utility.date.injection.FormatterDateTime
import com.example.utility.date.injection.FormatterTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateTimeBindingAdapter @Inject constructor(

    @FormatterDate
    private val dateFormatter: DateTimeFormatter,

    @FormatterTime
    private val timeFormatter: DateTimeFormatter,

    @FormatterDateTime
    private val dateTimeFormatter: DateTimeFormatter
) {
    @BindingAdapter("binding:date")
    fun TextView.setDate(dateTime: ZonedDateTime) {
        text = dateFormatter.format(dateTime)
    }

    @BindingAdapter("binding:time")
    fun TextView.setTime(dateTime: ZonedDateTime) {
        text = timeFormatter.format(dateTime)
    }

    @BindingAdapter("binding:dateAndTime")
    fun TextView.setDateAndTime(dateTime: ZonedDateTime) {
        text = dateTimeFormatter.format(dateTime)
    }
}
