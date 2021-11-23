package com.example.injection

import com.example.styling.ThemedAttributeProvider
import com.example.styling.ThemedAttributeProviderImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent

@InstallIn(
    value = [
        ActivityComponent::class,
        FragmentComponent::class
    ]
)
@Module
interface StylingConsumerModule {

    @Binds
    fun bindThemedAttributeProvider(instance: ThemedAttributeProviderImplementation) : ThemedAttributeProvider
}
