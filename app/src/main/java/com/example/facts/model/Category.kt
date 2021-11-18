package com.example.facts.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity
data class Category(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val name: String,

    val created: ZonedDateTime
)
