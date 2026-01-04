package com.jetbrains.kmpapp.feature.student.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_activities")
data class StudentActivityEntity(
    @PrimaryKey val id: Long,
    val studentId: String,
    val title: String,
    val subject: String,
    val questionCount: Int,
    val score: Int?,
    val durationMinutes: Int?,
    val hasInstantFeedback: Boolean,
    val smartSelectionEnabled: Boolean,
    val occurredAt: Long,
    val type: String
)
