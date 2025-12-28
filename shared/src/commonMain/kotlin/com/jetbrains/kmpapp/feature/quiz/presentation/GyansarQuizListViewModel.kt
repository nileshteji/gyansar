package com.jetbrains.kmpapp.feature.quiz.presentation

import com.jetbrains.kmpapp.feature.quiz.domain.Quiz
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.ObserveQuizzesUseCase
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.SeedQuizzesIfEmptyUseCase
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GyansarQuizListViewModel(
    private val observeQuizzes: ObserveQuizzesUseCase,
    private val seedQuizzesIfEmpty: SeedQuizzesIfEmptyUseCase
) : ViewModel() {
    @NativeCoroutinesState
    val quizzes: StateFlow<List<Quiz>> =
        observeQuizzes()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            seedQuizzesIfEmpty()
        }
    }
}
