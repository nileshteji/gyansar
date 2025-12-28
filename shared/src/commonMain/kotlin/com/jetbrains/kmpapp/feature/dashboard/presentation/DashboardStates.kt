package com.jetbrains.kmpapp.feature.dashboard.presentation

data class StudentDashboardState(
    val greeting: String,
    val subtext: String,
    val initials: String,
    val stats: List<StatState>,
    val practiceLabel: String,
    val practice: PracticeState,
    val recentTestsLabel: String,
    val recentTests: List<RecentTestState>,
    val navItems: List<String>,
    val selectedNavIndex: Int
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
    val date: String
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
