package com.jetbrains.kmpapp.feature.quiz.domain.usecase

import com.jetbrains.kmpapp.feature.quiz.domain.QuestionDraft
import com.jetbrains.kmpapp.feature.quiz.domain.QuizRepository

class AddQuestionUseCase(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(quizId: Long, question: QuestionDraft): Long {
        return repository.addQuestion(quizId, question)
    }
}
