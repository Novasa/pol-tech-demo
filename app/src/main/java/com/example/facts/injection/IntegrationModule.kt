package com.example.facts.injection

import com.example.utility.date.injection.DateTimeModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module(
    includes = [
        DateTimeModule::class]
)
interface IntegrationModule
