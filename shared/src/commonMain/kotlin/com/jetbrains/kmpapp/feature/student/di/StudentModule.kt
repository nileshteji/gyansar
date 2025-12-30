package com.jetbrains.kmpapp.feature.student.di

import com.jetbrains.kmpapp.feature.student.data.DefaultStudentProgressRepository
import com.jetbrains.kmpapp.feature.student.domain.StudentProgressRepository
import com.jetbrains.kmpapp.feature.student.domain.usecase.CreateAiTestUseCase
import com.jetbrains.kmpapp.feature.student.domain.usecase.ObserveActiveStudentUseCase
import com.jetbrains.kmpapp.feature.student.domain.usecase.ObserveStudentDashboardUseCase
import com.jetbrains.kmpapp.feature.student.domain.usecase.SetActiveStudentUseCase
import org.koin.dsl.module

val studentModule = module {
    single<StudentProgressRepository> { DefaultStudentProgressRepository(get()) }
    factory { ObserveActiveStudentUseCase(get()) }
    factory { SetActiveStudentUseCase(get()) }
    factory { ObserveStudentDashboardUseCase(get()) }
    factory { CreateAiTestUseCase(get(), get(), get()) }
}
