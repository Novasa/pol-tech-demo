package com.example.utility.date.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.format.DateTimeFormatter

@InstallIn(SingletonComponent::class)
@Module
class DateTimeModule {


    @FormatterDate
    @Provides
    fun provideDateFormatter(): DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @FormatterTime
    @Provides
    fun provideTimeFormatter(): DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    @FormatterDateTime
    @Provides
    fun provideDateTimeFormatter(): DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSSZ")
}
