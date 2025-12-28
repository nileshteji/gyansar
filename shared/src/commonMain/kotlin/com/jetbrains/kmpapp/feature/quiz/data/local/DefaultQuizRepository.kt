package com.jetbrains.kmpapp.feature.quiz.data.local

import com.jetbrains.kmpapp.feature.quiz.domain.QuestionDraft
import com.jetbrains.kmpapp.feature.quiz.domain.Quiz
import com.jetbrains.kmpapp.feature.quiz.domain.QuizRepository
import com.jetbrains.kmpapp.feature.quiz.domain.QuizWithQuestions
import kotlinx.coroutines.flow.Flow

class DefaultQuizRepository(
    private val localDataSource: QuizLocalDataSource
) : QuizRepository {
    override fun observeQuizzes(): Flow<List<Quiz>> = localDataSource.observeQuizzes()

    override fun observeQuizWithQuestions(quizId: Long): Flow<QuizWithQuestions?> =
        localDataSource.observeQuizWithQuestions(quizId)

    override suspend fun createQuiz(title: String, subject: String, lastScore: Int?): Long {
        return localDataSource.createQuiz(title, subject, lastScore)
    }

    override suspend fun addQuestion(quizId: Long, question: QuestionDraft): Long {
        return localDataSource.addQuestion(quizId, question)
    }

    override suspend fun seedIfEmpty() {
        // No-op: seed data is intentionally disabled.
    }
}
