package com.jetbrains.kmpapp.feature.student.data.local

import com.jetbrains.kmpapp.feature.student.data.local.dao.StudentDao
import com.jetbrains.kmpapp.feature.student.data.local.entity.FlashcardEntity
import com.jetbrains.kmpapp.feature.student.data.local.entity.StudentActivityEntity
import com.jetbrains.kmpapp.feature.student.data.local.entity.StudentProfileEntity
import com.jetbrains.kmpapp.feature.student.data.local.StudentLocalDataSource
import com.jetbrains.kmpapp.feature.student.domain.ActivityType
import com.jetbrains.kmpapp.feature.student.domain.Flashcard
import com.jetbrains.kmpapp.feature.student.domain.StudentActivity
import com.jetbrains.kmpapp.feature.student.domain.StudentProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant

class RoomStudentLocalDataSource(
    private val studentDao: StudentDao
) : StudentLocalDataSource {
    override fun observeActiveStudent(): Flow<StudentProfile?> {
        return studentDao.observeActiveStudent().map { entity -> entity?.toDomain() }
    }

    override fun observeActivities(studentId: String): Flow<List<StudentActivity>> {
        return studentDao.observeActivities(studentId).map { list -> list.map { it.toDomain() } }
    }

    override fun observeFlashcards(studentId: String): Flow<List<Flashcard>> {
        return studentDao.observeFlashcards(studentId).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun setActiveStudent(id: String, displayName: String?) {
        studentDao.clearActive()
        val profile = StudentProfileEntity(
            id = id,
            displayName = displayName ?: "Student $id",
            isActive = true
        )
        studentDao.upsertStudent(profile)
    }

    override suspend fun upsertActivity(activity: StudentActivity) {
        studentDao.upsertActivity(activity.toEntity())
    }

    override suspend fun upsertFlashcards(cards: List<Flashcard>) {
        studentDao.upsertFlashcards(cards.map { it.toEntity() })
    }

    private fun StudentProfileEntity.toDomain(): StudentProfile {
        return StudentProfile(
            id = id,
            displayName = displayName,
            isActive = isActive
        )
    }

    private fun StudentActivityEntity.toDomain(): StudentActivity {
        return StudentActivity(
            id = id,
            studentId = studentId,
            title = title,
            subject = subject,
            questionCount = questionCount,
            score = score,
            durationMinutes = durationMinutes,
            hasInstantFeedback = hasInstantFeedback,
            smartSelectionEnabled = smartSelectionEnabled,
            occurredAt = Instant.fromEpochMilliseconds(occurredAt),
            type = ActivityType.valueOf(type)
        )
    }

    private fun FlashcardEntity.toDomain(): Flashcard {
        return Flashcard(
            id = id,
            studentId = studentId,
            prompt = prompt,
            answer = answer,
            topic = topic,
            createdAt = Instant.fromEpochMilliseconds(createdAt),
            mastery = mastery,
            isStarred = isStarred
        )
    }

    private fun StudentActivity.toEntity(): StudentActivityEntity {
        return StudentActivityEntity(
            id = id,
            studentId = studentId,
            title = title,
            subject = subject,
            questionCount = questionCount,
            score = score,
            durationMinutes = durationMinutes,
            hasInstantFeedback = hasInstantFeedback,
            smartSelectionEnabled = smartSelectionEnabled,
            occurredAt = occurredAt.toEpochMilliseconds(),
            type = type.name
        )
    }

    private fun Flashcard.toEntity(): FlashcardEntity {
        return FlashcardEntity(
            id = id,
            studentId = studentId,
            prompt = prompt,
            answer = answer,
            topic = topic,
            createdAt = createdAt.toEpochMilliseconds(),
            mastery = mastery,
            isStarred = isStarred
        )
    }
}
