package com.example.dogfacts.repository

import com.example.dogfacts.model.DogFact

interface DogFactsRepository {
    suspend fun getDogFacts(count: Int): List<DogFact>
}
