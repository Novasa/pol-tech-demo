package com.example.catfacts.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatUser(

    @SerialName("name")
    val name: String
)
