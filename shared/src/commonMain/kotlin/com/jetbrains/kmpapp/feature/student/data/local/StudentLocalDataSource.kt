package com.jetbrains.kmpapp.feature.student.data.local

import com.jetbrains.kmpapp.feature.student.domain.Flashcard
import com.jetbrains.kmpapp.feature.student.domain.StudentActivity
import com.jetbrains.kmpapp.feature.student.domain.StudentProfile
import kotlinx.coroutines.flow.Flow

interface StudentLocalDataSource {
    fun observeActiveStudent(): Flow<StudentProfile?>
    fun observeActivities(studentId: String): Flow<List<StudentActivity>>
    fun observeFlashcards(studentId: String): Flow<List<Flashcard>>
    suspend fun setActiveStudent(id: String, displayName: String?)
    suspend fun upsertActivity(activity: StudentActivity)
    suspend fun upsertFlashcards(cards: List<Flashcard>)
}
