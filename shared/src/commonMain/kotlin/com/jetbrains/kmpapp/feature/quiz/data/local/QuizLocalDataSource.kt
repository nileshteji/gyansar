package com.jetbrains.kmpapp.feature.quiz.data.local

import com.jetbrains.kmpapp.feature.quiz.domain.QuestionDraft
import com.jetbrains.kmpapp.feature.quiz.domain.Quiz
import com.jetbrains.kmpapp.feature.quiz.domain.QuizWithQuestions
import kotlinx.coroutines.flow.Flow

interface QuizLocalDataSource {
    fun observeQuizzes(): Flow<List<Quiz>>
    fun observeQuizWithQuestions(quizId: Long): Flow<QuizWithQuestions?>
    suspend fun createQuiz(title: String, subject: String, lastScore: Int? = null): Long
    suspend fun addQuestion(quizId: Long, question: QuestionDraft): Long
}
