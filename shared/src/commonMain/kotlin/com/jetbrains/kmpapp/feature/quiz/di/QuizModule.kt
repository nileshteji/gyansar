package com.jetbrains.kmpapp.feature.quiz.di

import com.jetbrains.kmpapp.feature.quiz.data.local.DefaultQuizRepository
import com.jetbrains.kmpapp.feature.quiz.domain.QuizRepository
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.AddQuestionUseCase
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.CreateQuizUseCase
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.CreateSamplePracticeQuizUseCase
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.ObserveQuizWithQuestionsUseCase
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.ObserveQuizzesUseCase
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.SeedQuizzesIfEmptyUseCase
import com.jetbrains.kmpapp.feature.quiz.presentation.GyansarQuizDetailViewModel
import com.jetbrains.kmpapp.feature.quiz.presentation.GyansarQuizEditorViewModel
import com.jetbrains.kmpapp.feature.quiz.presentation.GyansarQuizListViewModel
import org.koin.dsl.module

val quizModule = module {
    single<QuizRepository> { DefaultQuizRepository(get()) }
    factory { ObserveQuizzesUseCase(get()) }
    factory { ObserveQuizWithQuestionsUseCase(get()) }
    factory { CreateQuizUseCase(get()) }
    factory { AddQuestionUseCase(get()) }
    factory { SeedQuizzesIfEmptyUseCase(get()) }
    factory { CreateSamplePracticeQuizUseCase(get()) }

    factory { GyansarQuizListViewModel(get(), get()) }
    factory { GyansarQuizDetailViewModel(get()) }
    factory { GyansarQuizEditorViewModel(get(), get(), get()) }
}
