package com.jetbrains.kmpapp.feature.dashboard.presentation

import com.jetbrains.kmpapp.feature.quiz.domain.Quiz
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.ObserveQuizzesUseCase
import com.jetbrains.kmpapp.feature.wireframe.presentation.GyansarWireframeData
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class GyansarTutorDashboardViewModel(
    observeQuizzes: ObserveQuizzesUseCase
) : ViewModel() {
    private val baseState = GyansarWireframeData.gallery.tutorDashboard

    val state: StateFlow<TutorDashboardState> =
        observeQuizzes()
            .map { quizzes ->
                baseState.copy(recentQuizzes = quizzes.toTutorQuizState())
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), baseState)

    private fun List<Quiz>.toTutorQuizState(): List<TutorQuizState> {
        return map { quiz ->
            TutorQuizState(
                quizId = quiz.id,
                title = quiz.title,
                subject = quiz.subject,
                createdLabel = quiz.createdAt.toString()
            )
        }
    }
}
