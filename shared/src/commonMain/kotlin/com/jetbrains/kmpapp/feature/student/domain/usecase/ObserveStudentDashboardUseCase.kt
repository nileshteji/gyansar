package com.jetbrains.kmpapp.feature.student.domain.usecase

import com.jetbrains.kmpapp.feature.student.domain.ActivityType
import com.jetbrains.kmpapp.feature.student.domain.StudentActivity
import com.jetbrains.kmpapp.feature.student.domain.StudentDashboardSnapshot
import com.jetbrains.kmpapp.feature.student.domain.StudentProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ObserveStudentDashboardUseCase(
    private val repository: StudentProgressRepository,
    private val clock: Clock = Clock.System,
    private val timeZone: TimeZone = TimeZone.currentSystemDefault()
) {
    operator fun invoke(studentId: String): Flow<StudentDashboardSnapshot> {
        val studentFlow = repository.observeActiveStudent()
        val activitiesFlow = repository.observeActivities(studentId)
        val flashcardsFlow = repository.observeFlashcards(studentId)
        return combine(studentFlow, activitiesFlow, flashcardsFlow) { student, activities, flashcards ->
            val filteredActivities = activities.filter { it.studentId == studentId }
            StudentDashboardSnapshot(
                student = student,
                activities = filteredActivities,
                flashcards = flashcards,
                streakDays = filteredActivities.calculateStreakDays(),
                activeDays = filteredActivities.distinctBy { it.occurredAt.toDayKey() }.size,
                totalQuizzes = filteredActivities.count { it.type == ActivityType.QUIZ || it.type == ActivityType.TEST },
                totalFlashcards = flashcards.size
            )
        }
    }

    private fun List<StudentActivity>.calculateStreakDays(): Int {
        if (isEmpty()) return 0
        val days = map { it.occurredAt.toDayKey() }.distinct().sortedDescending()
        val todayKey = clock.now().toDayKey()
        var streak = 0
        var cursor = todayKey
        for (day in days) {
            if (day == cursor) {
                streak += 1
                cursor = cursor - 1
            } else if (day < cursor) {
                break
            }
        }
        return streak
    }

    private fun Instant.toDayKey(): Int {
        val date = toLocalDateTime(timeZone).date
        return date.year * 10000 + date.monthNumber * 100 + date.dayOfMonth
    }
}
