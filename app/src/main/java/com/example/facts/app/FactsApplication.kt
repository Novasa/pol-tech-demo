package com.example.facts.app

import android.app.Application
import androidx.databinding.DataBindingUtil
import com.example.facts.BuildConfig
import com.example.facts.databinding.DataBindingComponentImplementation
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class FactsApplication : Application() {

    @Inject
    lateinit var dataBindingComponent: DataBindingComponentImplementation

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        DataBindingUtil.setDefaultComponent(dataBindingComponent)
    }
}