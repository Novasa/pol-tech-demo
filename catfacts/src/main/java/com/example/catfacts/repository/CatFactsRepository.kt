package com.example.catfacts.repository

import com.example.catfacts.model.CatFact
import com.example.catfacts.model.CatUser
import com.example.utility.coroutines.FlowChannel
import com.example.utility.coroutines.FlowOutChannel

interface CatFactsRepository {
    suspend fun getCatFacts(): FlowChannel<Int, List<CatFact>>
    suspend fun getCatUser(): FlowOutChannel<CatUser>
    suspend fun updateCatUser(): FlowChannel<CatUser, CatUser>
}
