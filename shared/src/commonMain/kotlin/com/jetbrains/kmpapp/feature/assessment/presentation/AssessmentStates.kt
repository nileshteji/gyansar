package com.jetbrains.kmpapp.feature.assessment.presentation

data class CreateTestConfigState(
    val title: String,
    val documentName: String,
    val questionTypeLabel: String,
    val questionTypes: List<String>,
    val selectedQuestionType: Int,
    val difficultyLabel: String,
    val difficultyLevels: List<String>,
    val selectedDifficulty: Int,
    val questionCountLabel: String,
    val questionRange: IntRange,
    val defaultQuestionCount: Int,
    val timeLimitLabel: String,
    val timeLimitMinutes: String,
    val generateButtonText: String
)

data class AssessmentState(
    val progressLabel: String,
    val timerLabel: String,
    val flagLabel: String,
    val questionText: String,
    val answers: List<AnswerOptionState>,
    val previousLabel: String,
    val hintLabel: String,
    val nextLabel: String
)

data class AnswerOptionState(
    val text: String,
    val selected: Boolean
)

data class PerformanceAnalyticsState(
    val title: String,
    val score: String,
    val accuracyLabel: String,
    val accuracy: String,
    val timeLabel: String,
    val time: String,
    val performanceLabel: String,
    val topicPerformance: List<TopicPerformanceState>,
    val reviewLabel: String,
    val reviewAnswer: ReviewAnswerState
)

data class TopicPerformanceState(
    val label: String,
    val percent: Int
)

data class ReviewAnswerState(
    val question: String,
    val wrongLabel: String,
    val wrongAnswer: String,
    val correctLabel: String,
    val correctAnswer: String,
    val cta: String
)
