package com.jetbrains.kmpapp.feature.quiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.jetbrains.kmpapp.feature.quiz.data.local.entity.QuestionEntity
import com.jetbrains.kmpapp.feature.quiz.data.local.entity.QuizEntity
import com.jetbrains.kmpapp.feature.quiz.data.local.entity.QuizWithQuestionsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    @Query("SELECT * FROM quizzes ORDER BY createdAt DESC")
    fun observeQuizzes(): Flow<List<QuizEntity>>

    @Transaction
    @Query("SELECT * FROM quizzes WHERE id = :quizId")
    fun observeQuizWithQuestions(quizId: Long): Flow<QuizWithQuestionsEntity?>

    @Insert
    suspend fun insertQuiz(quiz: QuizEntity): Long

    @Insert
    suspend fun insertQuestion(question: QuestionEntity): Long

    @Query("SELECT COUNT(*) FROM quizzes")
    suspend fun countQuizzes(): Long
}
