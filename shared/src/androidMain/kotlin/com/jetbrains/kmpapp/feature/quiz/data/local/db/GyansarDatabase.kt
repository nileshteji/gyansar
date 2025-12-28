package com.jetbrains.kmpapp.feature.quiz.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jetbrains.kmpapp.feature.quiz.data.local.dao.QuizDao
import com.jetbrains.kmpapp.feature.quiz.data.local.entity.QuestionEntity
import com.jetbrains.kmpapp.feature.quiz.data.local.entity.QuizEntity

@Database(
    entities = [QuizEntity::class, QuestionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GyansarDatabase : RoomDatabase() {
    abstract fun quizDao(): QuizDao
}
