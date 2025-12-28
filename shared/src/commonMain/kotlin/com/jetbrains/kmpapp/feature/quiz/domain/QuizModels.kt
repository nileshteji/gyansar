package com.jetbrains.kmpapp.feature.quiz.domain

data class Quiz(
    val id: Long,
    val title: String,
    val subject: String,
    val createdAt: Long,
    val lastScore: Int?
)

data class Question(
    val id: Long,
    val quizId: Long,
    val prompt: String,
    val options: List<String>,
    val correctAnswer: String,
    val difficulty: String,
    val bloomLevel: String,
    val createdAt: Long
)

data class QuizWithQuestions(
    val quiz: Quiz,
    val questions: List<Question>
)

data class QuestionDraft(
    val prompt: String,
    val options: List<String>,
    val correctAnswer: String,
    val difficulty: String,
    val bloomLevel: String
)
