package com.example.dogfacts.repository

import com.example.dogfacts.model.DogFact
import com.example.dogfacts.service.DogFactsService
import com.example.network.repository.FlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class DogFactsRepository @Inject constructor(
    private val service: DogFactsService

) : FlowRepository<List<@JvmSuppressWildcards DogFact>> {

    override val flow: Flow<List<DogFact>> = flow {
        Timber.d("Getting dog facts...")

        val result = service.getDogFacts(10)

        Timber.d("Result: $result")

        emit(result)

    }
}
