package com.example.dogfacts.repository

import com.example.dogfacts.model.DogFact
import com.example.dogfacts.service.DogFactsService
import com.example.network.repository.Repository
import timber.log.Timber
import javax.inject.Inject

class DogFactsRepository @Inject constructor(
    private val service: DogFactsService
) : Repository<Int, List<@JvmSuppressWildcards DogFact>> {

    override suspend fun get(input: Int): List<DogFact> {
        Timber.d("Getting dog facts...")

        return service.getDogFacts(input).also {
            Timber.d("Result: $it")
        }
    }
}
