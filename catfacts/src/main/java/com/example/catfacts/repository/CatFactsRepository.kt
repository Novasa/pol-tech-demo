package com.example.catfacts.repository

import com.example.catfacts.model.CatFact
import com.example.catfacts.service.CatFactsService
import com.example.network.repository.Repository
import timber.log.Timber
import javax.inject.Inject

class CatFactsRepository @Inject constructor(
    private val service: CatFactsService
) : Repository<Int, List<@JvmSuppressWildcards CatFact>> {

    override suspend fun get(input: Int): List<CatFact> {
        Timber.d("Getting cat facts...")

        return service.getCatFacts(input, 200).data.also {
            Timber.d("Result: $it")
        }
    }
}
