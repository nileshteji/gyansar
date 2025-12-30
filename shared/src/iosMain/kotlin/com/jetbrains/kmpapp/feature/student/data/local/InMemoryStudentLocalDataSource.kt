package com.jetbrains.kmpapp.feature.student.data.local

import com.jetbrains.kmpapp.feature.student.data.local.StudentLocalDataSource
import com.jetbrains.kmpapp.feature.student.domain.Flashcard
import com.jetbrains.kmpapp.feature.student.domain.StudentActivity
import com.jetbrains.kmpapp.feature.student.domain.StudentProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class InMemoryStudentLocalDataSource : StudentLocalDataSource {
    private val students = MutableStateFlow<Map<String, StudentProfile>>(emptyMap())
    private val activities = MutableStateFlow<Map<String, List<StudentActivity>>>(emptyMap())
    private val flashcards = MutableStateFlow<Map<String, List<Flashcard>>>(emptyMap())

    override fun observeActiveStudent(): Flow<StudentProfile?> {
        return students.map { profiles -> profiles.values.firstOrNull { it.isActive } }
    }

    override fun observeActivities(studentId: String): Flow<List<StudentActivity>> {
        return activities.map { it[studentId].orEmpty() }
    }

    override fun observeFlashcards(studentId: String): Flow<List<Flashcard>> {
        return flashcards.map { it[studentId].orEmpty() }
    }

    override suspend fun setActiveStudent(id: String, displayName: String?) {
        val existing = students.value.toMutableMap()
        val updatedProfiles = existing.mapValues { (_, profile) -> profile.copy(isActive = false) }.toMutableMap()
        updatedProfiles[id] = StudentProfile(
            id = id,
            displayName = displayName ?: "Student $id",
            isActive = true
        )
        students.value = updatedProfiles
    }

    override suspend fun upsertActivity(activity: StudentActivity) {
        val current = activities.value[activity.studentId].orEmpty()
        activities.value = activities.value + (activity.studentId to (listOf(activity) + current.filterNot { it.id == activity.id }))
    }

    override suspend fun upsertFlashcards(cards: List<Flashcard>) {
        if (cards.isEmpty()) return
        val studentId = cards.first().studentId
        val current = flashcards.value[studentId].orEmpty()
        val replaced = cards.associateBy { it.id }
        val merged = current.filterNot { replaced.containsKey(it.id) } + cards
        flashcards.value = flashcards.value + (studentId to merged)
    }
}
