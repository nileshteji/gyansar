package com.jetbrains.kmpapp.feature.quiz.data.local

import com.jetbrains.kmpapp.feature.quiz.data.OptionsCodec
import com.jetbrains.kmpapp.feature.quiz.data.local.db.GyansarDatabase
import com.jetbrains.kmpapp.feature.quiz.data.local.entity.QuestionEntity
import com.jetbrains.kmpapp.feature.quiz.data.local.entity.QuizEntity
import com.jetbrains.kmpapp.feature.quiz.data.local.entity.QuizWithQuestionsEntity
import com.jetbrains.kmpapp.feature.quiz.domain.Question
import com.jetbrains.kmpapp.feature.quiz.domain.QuestionDraft
import com.jetbrains.kmpapp.feature.quiz.domain.Quiz
import com.jetbrains.kmpapp.feature.quiz.domain.QuizWithQuestions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomQuizLocalDataSource(
    private val database: GyansarDatabase
) : QuizLocalDataSource {
    private val quizDao = database.quizDao()

    override fun observeQuizzes(): Flow<List<Quiz>> {
        return quizDao.observeQuizzes().map { quizzes ->
            quizzes.map { it.toDomain() }
        }
    }

    override fun observeQuizWithQuestions(quizId: Long): Flow<QuizWithQuestions?> {
        return quizDao.observeQuizWithQuestions(quizId).map { entity ->
            entity?.toDomain()
        }
    }

    override suspend fun createQuiz(title: String, subject: String, lastScore: Int?): Long {
        val now = System.currentTimeMillis()
        val quiz = QuizEntity(
            title = title,
            subject = subject,
            createdAt = now,
            lastScore = lastScore
        )
        return quizDao.insertQuiz(quiz)
    }

    override suspend fun addQuestion(quizId: Long, question: QuestionDraft): Long {
        val now = System.currentTimeMillis()
        val entity = QuestionEntity(
            quizId = quizId,
            prompt = question.prompt,
            optionsJson = OptionsCodec.encode(question.options),
            correctAnswer = question.correctAnswer,
            difficulty = question.difficulty,
            bloomLevel = question.bloomLevel,
            createdAt = now
        )
        return quizDao.insertQuestion(entity)
    }

    private fun QuizEntity.toDomain(): Quiz {
        return Quiz(
            id = id,
            title = title,
            subject = subject,
            createdAt = createdAt,
            lastScore = lastScore
        )
    }

    private fun QuestionEntity.toDomain(): Question {
        return Question(
            id = id,
            quizId = quizId,
            prompt = prompt,
            options = OptionsCodec.decode(optionsJson),
            correctAnswer = correctAnswer,
            difficulty = difficulty,
            bloomLevel = bloomLevel,
            createdAt = createdAt
        )
    }

    private fun QuizWithQuestionsEntity.toDomain(): QuizWithQuestions {
        return QuizWithQuestions(
            quiz = quiz.toDomain(),
            questions = questions.map { it.toDomain() }
        )
    }
}
