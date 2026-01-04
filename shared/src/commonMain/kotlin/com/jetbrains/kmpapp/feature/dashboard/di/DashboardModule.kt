package com.jetbrains.kmpapp.feature.dashboard.di

import com.jetbrains.kmpapp.feature.dashboard.presentation.GyansarStudentDashboardViewModel
import com.jetbrains.kmpapp.feature.dashboard.presentation.GyansarTutorDashboardViewModel
import com.jetbrains.kmpapp.feature.dashboard.presentation.StudentIdentityViewModel
import com.jetbrains.kmpapp.feature.student.domain.usecase.CreateAiTestUseCase
import com.jetbrains.kmpapp.feature.student.domain.usecase.ObserveActiveStudentUseCase
import com.jetbrains.kmpapp.feature.student.domain.usecase.ObserveStudentDashboardUseCase
import org.koin.dsl.module

val dashboardModule = module {
    factory { GyansarStudentDashboardViewModel(get(), get(), get()) }
    factory { GyansarTutorDashboardViewModel(get()) }
    factory { StudentIdentityViewModel(get()) }
}
