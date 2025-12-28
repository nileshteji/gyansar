package com.jetbrains.kmpapp.feature.quiz.domain.usecase

import com.jetbrains.kmpapp.feature.quiz.domain.QuizRepository

class SeedQuizzesIfEmptyUseCase(
    private val repository: QuizRepository
) {
    suspend operator fun invoke() {
        repository.seedIfEmpty()
    }
}
