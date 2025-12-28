package com.jetbrains.kmpapp.feature.dashboard.presentation

import com.jetbrains.kmpapp.feature.quiz.domain.Quiz
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.ObserveQuizzesUseCase
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.SeedQuizzesIfEmptyUseCase
import com.jetbrains.kmpapp.feature.wireframe.presentation.GyansarWireframeData
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class GyansarStudentDashboardViewModel(
    private val observeQuizzes: ObserveQuizzesUseCase,
    private val seedQuizzesIfEmpty: SeedQuizzesIfEmptyUseCase
) : ViewModel() {
    private val baseState = GyansarWireframeData.gallery.studentDashboard.copy(
        recentTests = emptyList()
    )

    @NativeCoroutinesState
    val state: StateFlow<StudentDashboardState> =
        observeQuizzes()
            .map { quizzes -> baseState.copy(recentTests = quizzes.toRecentTests()) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), baseState)

    init {
        viewModelScope.launch {
            seedQuizzesIfEmpty()
        }
    }

    private fun List<Quiz>.toRecentTests(): List<RecentTestState> {
        return take(3).map { quiz ->
            RecentTestState(
                quizId = quiz.id,
                subject = quiz.subject,
                score = quiz.lastScore?.let { "${it}%" } ?: "--",
                date = "New"
            )
        }
    }
}
