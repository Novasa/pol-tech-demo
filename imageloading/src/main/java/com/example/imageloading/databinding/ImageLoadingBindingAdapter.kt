package com.example.imageloading.databinding

import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.load
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageLoadingBindingAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) {

    @BindingAdapter("android:src")
    fun ImageView.loadImageResource(@DrawableRes resource: Int) {
        setImageResource(resource)
    }

    @ExperimentalCoilApi
    @BindingAdapter("binding:scope", "android:src")
    fun ImageView.loadImageUri(scope: CoroutineScope, uri: Uri) {
        scope.launch {
            load(uri, imageLoader) {
                crossfade(true)

            }.await()
        }
    }
}
