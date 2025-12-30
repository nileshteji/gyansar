package com.jetbrains.kmpapp.feature.dashboard.presentation

data class StudentDashboardState(
    val studentId: String,
    val greeting: String,
    val subtext: String,
    val initials: String,
    val stats: List<StatState> = emptyList(),
    val streakLabel: String,
    val streakValue: String,
    val recentTestsLabel: String,
    val recentTests: List<RecentTestState>,
    val flashcardLabel: String,
    val flashcards: List<FlashcardCardState>,
    val aiTestForm: AiTestFormState,
    val navItems: List<String>,
    val selectedNavIndex: Int,
    val gamification: GamificationState,
    val isStudentMissing: Boolean
)

data class StatState(
    val title: String,
    val value: String
)

data class PracticeState(
    val title: String,
    val subtitle: String,
    val cta: String
)

data class RecentTestState(
    val quizId: Long? = null,
    val subject: String,
    val score: String,
    val date: String,
    val title: String = "",
    val tags: List<String> = emptyList()
)

data class FlashcardCardState(
    val id: Long,
    val prompt: String,
    val topic: String,
    val mastery: Int,
    val isStarred: Boolean
)

data class AiTestFormState(
    val title: String,
    val subject: String,
    val topics: String,
    val questionCount: String,
    val timeLimitMinutes: String,
    val smartSelectionEnabled: Boolean,
    val instantFeedbackEnabled: Boolean,
    val submitLabel: String,
    val statusMessage: String? = null
)

data class GamificationState(
    val level: String,
    val xp: String,
    val badges: List<String>
)

data class TutorDashboardState(
    val title: String,
    val classesLabel: String,
    val classes: List<ClassSummaryState>,
    val announcementsLabel: String,
    val announcements: List<AnnouncementState>,
    val recentQuizzesLabel: String,
    val recentQuizzes: List<TutorQuizState>
)

data class ClassSummaryState(
    val title: String,
    val students: String,
    val activityLabel: String,
    val activity: String
)

data class AnnouncementState(
    val text: String
)

data class TutorQuizState(
    val quizId: Long,
    val title: String,
    val subject: String,
    val createdLabel: String
)
