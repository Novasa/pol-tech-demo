package com.example.dogfacts.repository

import com.example.dogfacts.model.DogImage
import com.example.dogfacts.service.DogImageService
import com.example.utility.coroutines.FlowChannel
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class DogImageChannel @Inject constructor(
    private val service: DogImageService
) : FlowChannel<Int, DogImage>() {

    override fun createFlow(params: Int) = flow {
        repeat(params) { i ->
            Timber.d("Loading image $i")
            val image = service.getDogImage()

            Timber.d("Emitting image $i")
            emit(image)
        }
    }
}
