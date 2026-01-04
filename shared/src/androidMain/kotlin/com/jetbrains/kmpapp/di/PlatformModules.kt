package com.jetbrains.kmpapp.di

import android.content.Context
import androidx.room.Room
import com.jetbrains.kmpapp.feature.quiz.data.local.QuizLocalDataSource
import com.jetbrains.kmpapp.feature.quiz.data.local.RoomQuizLocalDataSource
import com.jetbrains.kmpapp.feature.quiz.data.local.db.GyansarDatabase
import com.jetbrains.kmpapp.feature.student.data.local.RoomStudentLocalDataSource
import com.jetbrains.kmpapp.feature.student.data.local.StudentLocalDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single {
        val context: Context = get()
        Room.databaseBuilder(context, GyansarDatabase::class.java, "gyansar.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single<QuizLocalDataSource> { RoomQuizLocalDataSource(get()) }
    single<StudentLocalDataSource> { RoomStudentLocalDataSource(get<GyansarDatabase>().studentDao()) }
}
