package com.example.imageloading.injection

import android.content.Context
import coil.ImageLoader
import coil.bitmap.BitmapPool
import coil.imageLoader
import coil.memory.MemoryCache
import coil.request.DefaultRequestOptions
import coil.request.Disposable
import coil.request.ImageRequest
import coil.request.ImageResult
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ImageLoadingModule {

    @Singleton
    @Provides
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader = context.imageLoader
}
