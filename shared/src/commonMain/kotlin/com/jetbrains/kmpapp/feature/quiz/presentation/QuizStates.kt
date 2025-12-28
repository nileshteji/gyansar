package com.jetbrains.kmpapp.feature.quiz.presentation

data class QuizReviewState(
    val title: String,
    val questions: List<QuizQuestionState>,
    val editLabel: String,
    val deleteLabel: String,
    val addButtonText: String,
    val finalizeButtonText: String
)

data class QuizQuestionState(
    val question: String,
    val answers: String,
    val tags: List<String>
)

data class QuizAnalyticsState(
    val title: String,
    val tabs: List<String>,
    val selectedTabIndex: Int,
    val metrics: List<MetricState>,
    val scoreDistributionLabel: String,
    val histogramBars: List<Float>,
    val histogramLabels: List<String>,
    val toughestQuestionLabel: String,
    val toughestQuestion: String
)

data class MetricState(
    val title: String,
    val value: String
)
