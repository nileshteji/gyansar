package com.jetbrains.kmpapp.feature.quiz.data

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

object OptionsCodec {
    private val json = Json { ignoreUnknownKeys = true }
    private val listSerializer = ListSerializer(String.serializer())

    fun encode(options: List<String>): String {
        return json.encodeToString(listSerializer, options)
    }

    fun decode(raw: String): List<String> {
        if (raw.isBlank()) {
            return emptyList()
        }
        return json.decodeFromString(listSerializer, raw)
    }
}
