package com.example.catfacts.service

import com.example.catfacts.model.CatFactsResponse
import com.example.catfacts.model.CatUser
import retrofit2.http.*

interface CatFactsService {

    @GET("facts")
    suspend fun getCatFacts(
        @Query("limit") count: Int,
        @Query("max_length") maxLength: Int
    ): CatFactsResponse


    // These endpoints don't actually exist, they are just here for instrumented test examples
    @GET("bogus")
    suspend fun getCatUser(): CatUser

    @PUT("bogus")
    suspend fun updateCatUser(@Body user: CatUser)
}
