package com.example.facts.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.facts.databinding.Selectable

@Entity
data class Fact(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val text: String
)
