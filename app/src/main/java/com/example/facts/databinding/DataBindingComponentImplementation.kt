package com.example.facts.databinding

import androidx.databinding.DataBindingComponent
import com.example.imageloading.databinding.ImageLoadingBindingAdapter
import com.example.view.databinding.DateTimeBindingAdapter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataBindingComponentImplementation @Inject constructor(
    private val imageLoading: ImageLoadingBindingAdapter,
    private val dateTime: DateTimeBindingAdapter
) : DataBindingComponent {

    override fun getImageLoadingBindingAdapter(): ImageLoadingBindingAdapter = imageLoading
    override fun getDateTimeBindingAdapter(): DateTimeBindingAdapter = dateTime
}
