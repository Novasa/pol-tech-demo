package com.example.styling

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AnyRes
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import javax.inject.Inject

class ThemedAttributeProvider @Inject constructor(
    private val context: Context
) {

    @AnyRes
    fun getThemedAttribute(@AttrRes resId: Int): Int = TypedValue().also {
        context.theme.resolveAttribute(resId, it, true)
    }.resourceId

    @ColorInt
    fun getColor(@AttrRes resId: Int) = getThemedAttribute(resId).let {
        ContextCompat.getColor(context, it)
    }

    @Dimension
    fun getDimensionF(@AttrRes resId: Int) = getThemedAttribute(resId).let {
        context.resources.getDimension(it)
    }

    @Dimension
    fun getDimensionP(@AttrRes resId: Int) = getThemedAttribute(resId).let {
        context.resources.getDimensionPixelSize(it)
    }
}
