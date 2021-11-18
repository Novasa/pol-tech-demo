package com.example.dogfacts.repository

import com.example.dogfacts.model.DogImage
import com.example.dogfacts.service.DogImageService
import com.example.network.repository.FlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DogImageRepository @Inject constructor(
    private val service: DogImageService
) : FlowRepository<DogImage> {

    override val flow: Flow<DogImage>
        get() = flow {
            val image = service.getDogImage()
            emit(image)
        }
}
