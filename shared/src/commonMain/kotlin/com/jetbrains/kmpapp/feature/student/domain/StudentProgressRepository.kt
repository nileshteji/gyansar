package com.jetbrains.kmpapp.feature.student.domain

import kotlinx.coroutines.flow.Flow

interface StudentProgressRepository {
    fun observeActiveStudent(): Flow<StudentProfile?>
    fun observeActivities(studentId: String): Flow<List<StudentActivity>>
    fun observeFlashcards(studentId: String): Flow<List<Flashcard>>
    suspend fun setActiveStudent(id: String, displayName: String? = null)
    suspend fun logActivity(activity: StudentActivity)
    suspend fun saveFlashcards(cards: List<Flashcard>)
}
