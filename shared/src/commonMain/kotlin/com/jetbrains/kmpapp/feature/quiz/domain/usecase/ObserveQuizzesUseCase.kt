package com.jetbrains.kmpapp.feature.quiz.domain.usecase

import com.jetbrains.kmpapp.feature.quiz.domain.Quiz
import com.jetbrains.kmpapp.feature.quiz.domain.QuizRepository
import kotlinx.coroutines.flow.Flow

class ObserveQuizzesUseCase(
    private val repository: QuizRepository
) {
    operator fun invoke(): Flow<List<Quiz>> = repository.observeQuizzes()
}
