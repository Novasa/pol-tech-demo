package com.example.dogfacts.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DogFact(

    @SerialName("fact")
    val fact: String,
)
