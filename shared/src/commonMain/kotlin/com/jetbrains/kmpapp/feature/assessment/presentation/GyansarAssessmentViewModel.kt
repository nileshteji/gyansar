package com.jetbrains.kmpapp.feature.assessment.presentation

import com.jetbrains.kmpapp.feature.quiz.domain.Question
import com.jetbrains.kmpapp.feature.quiz.domain.QuizWithQuestions
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.ObserveQuizWithQuestionsUseCase
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class GyansarAssessmentViewModel(
    private val observeQuizWithQuestions: ObserveQuizWithQuestionsUseCase
) : ViewModel() {
    private val uiLabels = AssessmentState(
        progressLabel = "",
        timerLabel = "00:00",
        flagLabel = "Flag",
        questionText = "",
        answers = emptyList(),
        previousLabel = "< Previous",
        hintLabel = "Hint",
        nextLabel = "Next >"
    )
    private val quizId = MutableStateFlow<Long?>(null)
    private val currentIndex = MutableStateFlow(0)
    private val selectedIndex = MutableStateFlow<Int?>(null)
    private val emptyState = AssessmentState(
        progressLabel = "",
        timerLabel = "",
        flagLabel = "",
        questionText = "",
        answers = emptyList(),
        previousLabel = "",
        hintLabel = "",
        nextLabel = ""
    )

    val state: StateFlow<AssessmentState> =
        combine(
            quizId.flatMapLatest { id ->
                if (id == null) flowOf<QuizWithQuestions?>(null) else observeQuizWithQuestions(id)
            },
            currentIndex,
            selectedIndex
        ) { quiz, index, selected ->
            quiz?.toAssessmentState(index, selected) ?: emptyState
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyState)

    fun setQuizId(id: Long) {
        quizId.value = id
        currentIndex.value = 0
        selectedIndex.value = null
    }

    fun next() {
        currentIndex.value = currentIndex.value + 1
        selectedIndex.value = null
    }

    fun previous() {
        currentIndex.value = (currentIndex.value - 1).coerceAtLeast(0)
        selectedIndex.value = null
    }

    fun selectAnswer(index: Int) {
        selectedIndex.value = index
    }

    private fun QuizWithQuestions.toAssessmentState(
        index: Int,
        selected: Int?
    ): AssessmentState {
        val questions = questions
        if (questions.isEmpty()) {
            return emptyState
        }
        val safeIndex = index.coerceIn(0, questions.lastIndex)
        val question = questions[safeIndex]
        return AssessmentState(
            progressLabel = "Question ${safeIndex + 1} / ${questions.size}",
            timerLabel = uiLabels.timerLabel,
            flagLabel = uiLabels.flagLabel,
            questionText = question.prompt,
            answers = question.toAnswerOptions(selected),
            previousLabel = uiLabels.previousLabel,
            hintLabel = uiLabels.hintLabel,
            nextLabel = uiLabels.nextLabel
        )
    }

    private fun Question.toAnswerOptions(selected: Int?): List<AnswerOptionState> {
        return options.mapIndexed { index, option ->
            AnswerOptionState(text = option, selected = index == selected)
        }
    }
}
