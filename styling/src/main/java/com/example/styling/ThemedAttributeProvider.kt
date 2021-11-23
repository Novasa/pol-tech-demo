package com.example.styling

import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.Dimension

interface ThemedAttributeProvider {

    @ColorInt
    fun getColor(@AttrRes resId: Int): Int

    @Dimension
    fun getDimensionF(@AttrRes resId: Int): Float

    @Dimension
    fun getDimensionP(@AttrRes resId: Int): Int
}
