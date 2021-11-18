package com.example.dogfacts.service

import com.example.dogfacts.model.DogImage
import retrofit2.http.GET

interface DogImageService {

    @GET("breeds/image/random")
    suspend fun getDogImage() : DogImage
}
