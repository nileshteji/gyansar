package com.jetbrains.kmpapp.feature.student.domain.usecase

import com.jetbrains.kmpapp.feature.student.domain.StudentProgressRepository

class SetActiveStudentUseCase(
    private val repository: StudentProgressRepository
) {
    suspend operator fun invoke(id: String, displayName: String? = null) {
        repository.setActiveStudent(id, displayName)
    }
}
