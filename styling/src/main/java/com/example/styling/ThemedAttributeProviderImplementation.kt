package com.example.styling

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ThemedAttributeProviderImplementation @Inject constructor(
    @ActivityContext
    private val context: Context
) : ThemedAttributeProvider {

    private fun getThemedAttributeValue(@AttrRes resId: Int) = TypedValue().also {
        context.theme.resolveAttribute(resId, it, true)
    }

    @ColorInt
    override fun getColor(@AttrRes resId: Int) = getThemedAttributeValue(resId).let {
        ContextCompat.getColor(context, it.data)
    }

    @Dimension
    override fun getDimensionF(@AttrRes resId: Int) = getThemedAttributeValue(resId)
        .getDimension(context.resources.displayMetrics)

    @Dimension
    override fun getDimensionP(@AttrRes resId: Int) = getThemedAttributeValue(resId).let {
        TypedValue.complexToDimensionPixelSize(it.data, context.resources.displayMetrics)
    }
}
