package com.jetbrains.kmpapp.feature.quiz.domain.usecase

import com.jetbrains.kmpapp.feature.quiz.domain.QuestionDraft
import com.jetbrains.kmpapp.feature.quiz.domain.Quiz
import com.jetbrains.kmpapp.feature.quiz.domain.QuizRepository
import com.jetbrains.kmpapp.feature.quiz.domain.QuizWithQuestions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CreateSamplePracticeQuizUseCaseTest {
    @Test
    fun returnsCreatedQuizIdWithoutSeedingQuestions() = runBlocking {
        val repository = TrackingQuizRepository()
        val createQuizUseCase = CreateQuizUseCase(repository)
        val useCase = CreateSamplePracticeQuizUseCase(createQuizUseCase)

        val result = useCase()

        assertNotNull(result)
        assertEquals(1L, result)
        assertEquals(1, repository.createCalls)
        assertEquals(0, repository.addQuestionCalls)
        assertEquals("Practice Test", repository.lastTitle)
        assertEquals("Practice", repository.lastSubject)
    }
}

private class TrackingQuizRepository : QuizRepository {
    var createCalls = 0
    var addQuestionCalls = 0
    var lastTitle: String? = null
    var lastSubject: String? = null

    override fun observeQuizzes(): Flow<List<Quiz>> = flowOf(emptyList())

    override fun observeQuizWithQuestions(quizId: Long): Flow<QuizWithQuestions?> = flowOf(null)

    override suspend fun createQuiz(title: String, subject: String, lastScore: Int?): Long {
        createCalls += 1
        lastTitle = title
        lastSubject = subject
        return createCalls.toLong()
    }

    override suspend fun addQuestion(quizId: Long, question: QuestionDraft): Long {
        addQuestionCalls += 1
        return addQuestionCalls.toLong()
    }

    override suspend fun seedIfEmpty() = Unit
}
