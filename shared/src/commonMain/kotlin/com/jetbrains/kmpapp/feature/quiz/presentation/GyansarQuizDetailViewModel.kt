package com.jetbrains.kmpapp.feature.quiz.presentation

import com.jetbrains.kmpapp.feature.quiz.domain.QuizWithQuestions
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.ObserveQuizWithQuestionsUseCase
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class GyansarQuizDetailViewModel(
    private val observeQuizWithQuestions: ObserveQuizWithQuestionsUseCase
) : ViewModel() {
    private val quizId = MutableStateFlow<Long?>(null)

    val quiz: StateFlow<QuizWithQuestions?> =
        quizId.flatMapLatest { id ->
            if (id == null) {
                flowOf(null)
            } else {
                observeQuizWithQuestions(id)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun setQuizId(id: Long) {
        quizId.value = id
    }
}
