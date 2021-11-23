package com.example.catfacts.injection

import com.example.catfacts.model.CatFact
import com.example.catfacts.repository.CatFactsRepository
import com.example.network.repository.Repository
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        CatFactsServiceModule::class
    ]
)
interface CatFactsConsumerModule {

    @Binds
    fun bindCatFactsRepository(instance: CatFactsRepository) : Repository<Int, List<CatFact>>
}
