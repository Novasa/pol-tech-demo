package com.example.facts.app

import android.app.Application
import androidx.databinding.DataBindingUtil
import com.example.facts.databinding.DataBindingComponentImplementation
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.testing.CustomTestApplication
import timber.log.Timber
import javax.inject.Inject

abstract class FactsTestApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        println("FactsTestApplication onCreate")
        Timber.d("FactsTestApplication onCreate")
    }
}

@CustomTestApplication(FactsTestApplication::class)
interface HiltTestApplication
