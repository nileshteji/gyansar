package com.jetbrains.kmpapp

import android.app.Application
import android.content.Context
import com.jetbrains.kmpapp.di.initKoin
import org.koin.dsl.module

class MuseumApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            listOf(
                module {
                    single<Context> { this@MuseumApp }
                }
            )
        )
    }
}
