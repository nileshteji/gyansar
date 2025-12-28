package com.jetbrains.kmpapp.feature.quiz.domain.usecase

import com.jetbrains.kmpapp.feature.quiz.domain.QuizRepository
import com.jetbrains.kmpapp.feature.quiz.domain.QuizWithQuestions
import kotlinx.coroutines.flow.Flow

class ObserveQuizWithQuestionsUseCase(
    private val repository: QuizRepository
) {
    operator fun invoke(quizId: Long): Flow<QuizWithQuestions?> =
        repository.observeQuizWithQuestions(quizId)
}
