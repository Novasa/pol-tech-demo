package com.example.catfacts.repository

import com.example.catfacts.model.CatFact
import com.example.catfacts.service.CatFactsService
import com.example.network.repository.FlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class CatFactsRepository @Inject constructor(
    private val service: CatFactsService

) : FlowRepository<List<@JvmSuppressWildcards CatFact>> {

    override val flow: Flow<List<CatFact>> = flow {
        Timber.d("Getting cat facts...")

        val result = service.getCatFacts(10, 200).data
        Timber.d("Result: $result")
        emit(result)
    }
}