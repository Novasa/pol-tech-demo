package com.example.facts.model

import java.io.Serializable

data class WeirdoTypeArg(val cls: Class<out WeirdoType>) : Serializable
