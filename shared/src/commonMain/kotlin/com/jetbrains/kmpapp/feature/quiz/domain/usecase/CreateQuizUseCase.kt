package com.jetbrains.kmpapp.feature.quiz.domain.usecase

import com.jetbrains.kmpapp.feature.quiz.domain.QuizRepository

class CreateQuizUseCase(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(title: String, subject: String, lastScore: Int? = null): Long {
        return repository.createQuiz(title, subject, lastScore)
    }
}
