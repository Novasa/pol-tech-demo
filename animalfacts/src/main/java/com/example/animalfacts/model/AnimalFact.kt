package com.example.animalfacts.model

import androidx.annotation.DrawableRes

data class AnimalFact(
    val animal: Animal,
    val text: String,

    @DrawableRes
    val iconResource: Int
)
