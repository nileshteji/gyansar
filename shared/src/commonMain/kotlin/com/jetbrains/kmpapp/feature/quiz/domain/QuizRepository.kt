package com.jetbrains.kmpapp.feature.quiz.domain

import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    fun observeQuizzes(): Flow<List<Quiz>>
    fun observeQuizWithQuestions(quizId: Long): Flow<QuizWithQuestions?>
    suspend fun createQuiz(title: String, subject: String, lastScore: Int? = null): Long
    suspend fun addQuestion(quizId: Long, question: QuestionDraft): Long
    suspend fun seedIfEmpty()
}
