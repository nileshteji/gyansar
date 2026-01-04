package com.jetbrains.kmpapp.feature.student.domain

import kotlinx.datetime.Instant

data class StudentProfile(
    val id: String,
    val displayName: String,
    val isActive: Boolean
)

enum class ActivityType {
    QUIZ,
    TEST,
    FLASHCARD_REVIEW
}

data class StudentActivity(
    val id: Long,
    val studentId: String,
    val title: String,
    val subject: String,
    val questionCount: Int,
    val score: Int?,
    val durationMinutes: Int?,
    val hasInstantFeedback: Boolean,
    val smartSelectionEnabled: Boolean,
    val occurredAt: Instant,
    val type: ActivityType
)

data class Flashcard(
    val id: Long,
    val studentId: String,
    val prompt: String,
    val answer: String,
    val topic: String,
    val createdAt: Instant,
    val mastery: Int,
    val isStarred: Boolean
)

data class AiTestRequest(
    val studentId: String,
    val title: String,
    val subject: String,
    val topics: String,
    val questionCount: Int,
    val timeLimitMinutes: Int,
    val smartSelectionEnabled: Boolean,
    val instantFeedbackEnabled: Boolean
)

data class AiTestResult(
    val quizId: Long,
    val activity: StudentActivity,
    val flashcards: List<Flashcard>
)

data class StudentDashboardSnapshot(
    val student: StudentProfile?,
    val activities: List<StudentActivity>,
    val flashcards: List<Flashcard>,
    val streakDays: Int,
    val activeDays: Int,
    val totalQuizzes: Int,
    val totalFlashcards: Int
)
