package com.example.catfacts.service

import com.example.catfacts.model.CatFactsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CatFactsService {

    @GET("facts")
    suspend fun getCatFacts(
        @Query("limit") count: Int,
        @Query("max_length") maxLength: Int
    ) : CatFactsResponse
}
