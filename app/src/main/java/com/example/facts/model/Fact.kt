package com.example.facts.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Fact(

    @PrimaryKey
    val id: Long = 0L,

    val text: String
)