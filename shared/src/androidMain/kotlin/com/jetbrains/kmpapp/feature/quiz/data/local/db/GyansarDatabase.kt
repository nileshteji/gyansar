package com.jetbrains.kmpapp.feature.quiz.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jetbrains.kmpapp.feature.quiz.data.local.dao.QuizDao
import com.jetbrains.kmpapp.feature.quiz.data.local.entity.QuestionEntity
import com.jetbrains.kmpapp.feature.quiz.data.local.entity.QuizEntity
import com.jetbrains.kmpapp.feature.student.data.local.dao.StudentDao
import com.jetbrains.kmpapp.feature.student.data.local.entity.FlashcardEntity
import com.jetbrains.kmpapp.feature.student.data.local.entity.StudentActivityEntity
import com.jetbrains.kmpapp.feature.student.data.local.entity.StudentProfileEntity

@Database(
    entities = [
        QuizEntity::class,
        QuestionEntity::class,
        StudentProfileEntity::class,
        StudentActivityEntity::class,
        FlashcardEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class GyansarDatabase : RoomDatabase() {
    abstract fun quizDao(): QuizDao
    abstract fun studentDao(): StudentDao
}
