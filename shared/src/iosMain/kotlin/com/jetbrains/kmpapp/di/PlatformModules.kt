package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.feature.quiz.data.local.InMemoryQuizLocalDataSource
import com.jetbrains.kmpapp.feature.quiz.data.local.QuizLocalDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<QuizLocalDataSource> { InMemoryQuizLocalDataSource() }
}
