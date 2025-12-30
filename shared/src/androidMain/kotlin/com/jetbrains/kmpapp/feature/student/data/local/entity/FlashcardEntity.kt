package com.jetbrains.kmpapp.feature.student.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flashcards")
data class FlashcardEntity(
    @PrimaryKey val id: Long,
    val studentId: String,
    val prompt: String,
    val answer: String,
    val topic: String,
    val createdAt: Long,
    val mastery: Int,
    val isStarred: Boolean
)
