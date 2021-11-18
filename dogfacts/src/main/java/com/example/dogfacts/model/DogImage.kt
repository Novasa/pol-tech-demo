package com.example.dogfacts.model

import android.net.Uri
import com.example.network.serializer.UriSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime

@Serializable
data class DogImage(

    @SerialName("message")
    @Serializable(with = UriSerializer::class)
    val url: Uri,

    val status: String,

    @Contextual
    val date: ZonedDateTime = ZonedDateTime.now()
)
