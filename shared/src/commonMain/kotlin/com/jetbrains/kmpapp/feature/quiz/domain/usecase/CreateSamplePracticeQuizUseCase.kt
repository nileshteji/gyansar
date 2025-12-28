package com.jetbrains.kmpapp.feature.quiz.domain.usecase

class CreateSamplePracticeQuizUseCase(
    private val createQuizUseCase: CreateQuizUseCase
) {
    suspend operator fun invoke(): Long? {
        return createQuizUseCase(
            title = "Practice Test",
            subject = "Practice",
            lastScore = null
        )
    }
}
