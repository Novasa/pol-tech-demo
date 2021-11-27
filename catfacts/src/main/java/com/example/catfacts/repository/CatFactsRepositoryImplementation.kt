package com.example.catfacts.repository

import com.example.catfacts.model.CatFact
import com.example.catfacts.model.CatUser
import com.example.catfacts.service.CatFactsService
import com.example.utility.coroutines.flowChannel
import com.example.utility.coroutines.flowOutChannel
import timber.log.Timber
import javax.inject.Inject

class CatFactsRepositoryImplementation @Inject constructor(
    private val service: CatFactsService
) : CatFactsRepository {

    override suspend fun getCatFacts() = flowChannel<Int, List<CatFact>> { count ->
        Timber.d("Getting cat facts...")
        service.getCatFacts(count, 200).data.also {
            Timber.d("Result: $it")
            emit(it)
        }
    }

    override suspend fun getCatUser() = flowOutChannel<CatUser> {
        Timber.d("Getting cat user...")
        service.getCatUser().also {
            emit(it)
        }
    }

    override suspend fun updateCatUser() = flowChannel<CatUser, CatUser> { update ->
        Timber.d("Updating cat user...")
        service.updateCatUser(update)
        emit(service.getCatUser())
    }
}
