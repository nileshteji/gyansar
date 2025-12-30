package com.jetbrains.kmpapp.feature.student.domain.usecase

import com.jetbrains.kmpapp.feature.student.domain.StudentProfile
import com.jetbrains.kmpapp.feature.student.domain.StudentProgressRepository
import kotlinx.coroutines.flow.Flow

class ObserveActiveStudentUseCase(
    private val repository: StudentProgressRepository
) {
    operator fun invoke(): Flow<StudentProfile?> = repository.observeActiveStudent()
}
