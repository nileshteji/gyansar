package com.jetbrains.kmpapp.feature.student.data

import com.jetbrains.kmpapp.feature.student.data.local.StudentLocalDataSource
import com.jetbrains.kmpapp.feature.student.domain.Flashcard
import com.jetbrains.kmpapp.feature.student.domain.StudentActivity
import com.jetbrains.kmpapp.feature.student.domain.StudentProgressRepository
import com.jetbrains.kmpapp.feature.student.domain.StudentProfile
import kotlinx.coroutines.flow.Flow

class DefaultStudentProgressRepository(
    private val localDataSource: StudentLocalDataSource
) : StudentProgressRepository {
    override fun observeActiveStudent(): Flow<StudentProfile?> = localDataSource.observeActiveStudent()

    override fun observeActivities(studentId: String): Flow<List<StudentActivity>> {
        return localDataSource.observeActivities(studentId)
    }

    override fun observeFlashcards(studentId: String): Flow<List<Flashcard>> {
        return localDataSource.observeFlashcards(studentId)
    }

    override suspend fun setActiveStudent(id: String, displayName: String?) {
        localDataSource.setActiveStudent(id, displayName)
    }

    override suspend fun logActivity(activity: StudentActivity) {
        localDataSource.upsertActivity(activity)
    }

    override suspend fun saveFlashcards(cards: List<Flashcard>) {
        localDataSource.upsertFlashcards(cards)
    }
}
