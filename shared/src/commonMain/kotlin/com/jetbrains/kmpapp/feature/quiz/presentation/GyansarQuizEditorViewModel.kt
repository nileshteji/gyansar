package com.jetbrains.kmpapp.feature.quiz.presentation

import com.jetbrains.kmpapp.feature.quiz.domain.QuestionDraft
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.AddQuestionUseCase
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.CreateQuizUseCase
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.CreateSamplePracticeQuizUseCase
import com.rickclephas.kmp.observableviewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GyansarQuizEditorViewModel(
    private val createQuizUseCase: CreateQuizUseCase,
    private val addQuestionUseCase: AddQuestionUseCase,
    private val createSamplePracticeQuizUseCase: CreateSamplePracticeQuizUseCase
) : ViewModel() {
    private val lastCreatedQuizId = MutableStateFlow<Long?>(null)
    val currentQuizId: StateFlow<Long?> = lastCreatedQuizId

    suspend fun createQuiz(title: String, subject: String, lastScore: Int? = null): Long {
        val id = createQuizUseCase.invoke(title, subject, lastScore)
        lastCreatedQuizId.value = id
        return id
    }

    suspend fun addQuestion(quizId: Long, question: QuestionDraft): Long {
        return addQuestionUseCase(quizId, question)
    }

    suspend fun createSamplePracticeQuiz(): Long? {
        return createSamplePracticeQuizUseCase()
    }
}
