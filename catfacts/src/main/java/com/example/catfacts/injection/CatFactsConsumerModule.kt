package com.example.catfacts.injection

import com.example.catfacts.repository.CatFactsRepository
import com.example.catfacts.repository.CatFactsRepositoryImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
interface CatFactsConsumerModule {

    @Binds
    fun bindCatFactsRepository(instance: CatFactsRepositoryImplementation) : CatFactsRepository
}
