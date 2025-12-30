package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.feature.quiz.data.local.InMemoryQuizLocalDataSource
import com.jetbrains.kmpapp.feature.quiz.data.local.QuizLocalDataSource
import com.jetbrains.kmpapp.feature.student.data.local.InMemoryStudentLocalDataSource
import com.jetbrains.kmpapp.feature.student.data.local.StudentLocalDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<QuizLocalDataSource> { InMemoryQuizLocalDataSource() }
    single<StudentLocalDataSource> { InMemoryStudentLocalDataSource() }
}
