package com.example.network.serializer

import com.example.utility.date.injection.FormatterDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateTimeSerializer @Inject constructor(
    @FormatterDateTime private val dateFormatter: DateTimeFormatter
) : KSerializer<ZonedDateTime> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("android.net.Uri", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): ZonedDateTime = ZonedDateTime.parse(decoder.decodeString(), dateFormatter)
    override fun serialize(encoder: Encoder, value: ZonedDateTime) = encoder.encodeString(dateFormatter.format(value))
}
