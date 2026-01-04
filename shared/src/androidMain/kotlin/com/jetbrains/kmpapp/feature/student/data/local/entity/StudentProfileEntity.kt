package com.jetbrains.kmpapp.feature.student.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_profiles")
data class StudentProfileEntity(
    @PrimaryKey val id: String,
    val displayName: String,
    val isActive: Boolean
)
