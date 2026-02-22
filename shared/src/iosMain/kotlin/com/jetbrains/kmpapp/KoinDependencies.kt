package com.jetbrains.kmpapp

import com.jetbrains.kmpapp.feature.assessment.presentation.GyansarAssessmentViewModel
import com.jetbrains.kmpapp.feature.dashboard.presentation.GyansarStudentDashboardViewModel
import com.jetbrains.kmpapp.feature.dashboard.presentation.GyansarTutorDashboardViewModel
import com.jetbrains.kmpapp.feature.museum.data.MuseumRepository
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.AddQuestionUseCase
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.CreateQuizUseCase
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.CreateSamplePracticeQuizUseCase
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.ObserveQuizWithQuestionsUseCase
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.ObserveQuizzesUseCase
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.SeedQuizzesIfEmptyUseCase
import com.jetbrains.kmpapp.feature.quiz.presentation.GyansarQuizDetailViewModel
import com.jetbrains.kmpapp.feature.quiz.presentation.GyansarQuizEditorViewModel
import com.jetbrains.kmpapp.feature.quiz.presentation.GyansarQuizListViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KoinDependencies : KoinComponent {
    val museumRepository: MuseumRepository by inject()
    val observeQuizzesUseCase: ObserveQuizzesUseCase by inject()
    val seedQuizzesIfEmptyUseCase: SeedQuizzesIfEmptyUseCase by inject()
    val observeQuizWithQuestionsUseCase: ObserveQuizWithQuestionsUseCase by inject()
    val createQuizUseCase: CreateQuizUseCase by inject()
    val addQuestionUseCase: AddQuestionUseCase by inject()
    val createSamplePracticeQuizUseCase: CreateSamplePracticeQuizUseCase by inject()

    val studentDashboardViewModel: GyansarStudentDashboardViewModel by inject()
    val tutorDashboardViewModel: GyansarTutorDashboardViewModel by inject()
    val assessmentViewModel: GyansarAssessmentViewModel by inject()
    val quizListViewModel: GyansarQuizListViewModel by inject()
    val quizDetailViewModel: GyansarQuizDetailViewModel by inject()
    val quizEditorViewModel: GyansarQuizEditorViewModel by inject()
}

val koinDependencies = KoinDependencies()
