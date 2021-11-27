package com.example.dogfacts.repository

import com.example.dogfacts.model.DogFact
import com.example.dogfacts.service.DogFactsService
import timber.log.Timber
import javax.inject.Inject

class DogFactsRepositoryImplementation @Inject constructor(
    private val service: DogFactsService
) : DogFactsRepository {

    override suspend fun getDogFacts(count: Int): List<DogFact> {
        Timber.d("Getting dog facts...")

        return service.getDogFacts(count).also {
            Timber.d("Result: $it")
        }
    }
}
