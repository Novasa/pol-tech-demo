package com.example.catfacts.model

import kotlinx.serialization.Serializable

@Serializable
data class CatFactsResponse(

    val data: List<CatFact>
)
