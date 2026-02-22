package com.jetbrains.kmpapp.feature.quiz.data.local

import com.jetbrains.kmpapp.feature.quiz.domain.Question
import com.jetbrains.kmpapp.feature.quiz.domain.QuestionDraft
import com.jetbrains.kmpapp.feature.quiz.domain.Quiz
import com.jetbrains.kmpapp.feature.quiz.domain.QuizWithQuestions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import platform.Foundation.*

class InMemoryQuizLocalDataSource : QuizLocalDataSource {
    private val quizzes = MutableStateFlow<List<Quiz>>(emptyList())
    private val questions = MutableStateFlow<Map<Long, List<Question>>>(emptyMap())
    private var nextQuizId = 1L
    private var nextQuestionId = 1L

    override fun observeQuizzes(): Flow<List<Quiz>> = quizzes

    override fun observeQuizWithQuestions(quizId: Long): Flow<QuizWithQuestions?> {
        return combine(quizzes, questions) { quizList, questionMap ->
            val quiz = quizList.firstOrNull { it.id == quizId } ?: return@combine null
            QuizWithQuestions(
                quiz = quiz,
                questions = questionMap[quizId].orEmpty()
            )
        }
    }

    override suspend fun createQuiz(title: String, subject: String, lastScore: Int?): Long {
        val id = nextQuizId++
        val now = currentTimeMillis()
        val quiz = Quiz(
            id = id,
            title = title,
            subject = subject,
            createdAt = now,
            lastScore = lastScore
        )
        quizzes.value = listOf(quiz) + quizzes.value
        return id
    }

    override suspend fun addQuestion(quizId: Long, question: QuestionDraft): Long {
        val id = nextQuestionId++
        val now = currentTimeMillis()
        val questionEntity = Question(
            id = id,
            quizId = quizId,
            prompt = question.prompt,
            options = question.options,
            correctAnswer = question.correctAnswer,
            difficulty = question.difficulty,
            bloomLevel = question.bloomLevel,
            createdAt = now
        )
        val existing = questions.value[quizId].orEmpty()
        questions.value = questions.value + (quizId to (existing + questionEntity))
        return id
    }

    private fun currentTimeMillis(): Long {
        return (NSDate.date().timeIntervalSince1970 * 1000).toLong()
    }
}
