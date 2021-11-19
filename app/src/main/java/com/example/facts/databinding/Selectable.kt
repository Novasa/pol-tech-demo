package com.example.facts.databinding

class Selectable<T : Any>(
    val item: T,
    val text: String,
    var selected: Boolean = false
)
