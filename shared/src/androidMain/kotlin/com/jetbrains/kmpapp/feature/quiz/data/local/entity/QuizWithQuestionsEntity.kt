package com.jetbrains.kmpapp.feature.quiz.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class QuizWithQuestionsEntity(
    @Embedded
    val quiz: QuizEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "quizId"
    )
    val questions: List<QuestionEntity>
)
