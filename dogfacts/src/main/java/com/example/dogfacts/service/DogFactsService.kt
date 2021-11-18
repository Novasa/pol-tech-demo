package com.example.dogfacts.service

import com.example.dogfacts.model.DogFact
import retrofit2.http.GET
import retrofit2.http.Query

interface DogFactsService {

    @GET("resources/dogs")
    suspend fun getDogFacts(
        @Query("number") count: Int
    ) : List<DogFact>
}
