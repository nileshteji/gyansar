package com.jetbrains.kmpapp.feature.museum.di

import com.jetbrains.kmpapp.feature.museum.data.InMemoryMuseumStorage
import com.jetbrains.kmpapp.feature.museum.data.KtorMuseumApi
import com.jetbrains.kmpapp.feature.museum.data.MuseumApi
import com.jetbrains.kmpapp.feature.museum.data.MuseumRepository
import com.jetbrains.kmpapp.feature.museum.data.MuseumStorage
import com.jetbrains.kmpapp.feature.museum.presentation.DetailViewModel
import com.jetbrains.kmpapp.feature.museum.presentation.ListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val museumModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                // TODO Fix API so it serves application/json
                json(json, contentType = ContentType.Any)
            }
        }
    }

    single<MuseumApi> { KtorMuseumApi(get()) }
    single<MuseumStorage> { InMemoryMuseumStorage() }
    single {
        MuseumRepository(get(), get()).apply {
            initialize()
        }
    }

    factory { ListViewModel(get()) }
    factory { DetailViewModel(get()) }
}
