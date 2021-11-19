package com.example.dogfacts.repository

import com.example.dogfacts.model.DogImage
import com.example.dogfacts.service.DogImageService
import com.example.network.repository.FlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class DogImageRepository @Inject constructor(
    private val service: DogImageService
) : FlowRepository<Int, DogImage> {

    override fun flow(input: Int): Flow<DogImage> = flow {
        repeat(input) { i ->
            Timber.d("Loading image $i")
            val image = service.getDogImage()

            Timber.d("Emitting image $i")
            emit(image)
        }
    }
}
