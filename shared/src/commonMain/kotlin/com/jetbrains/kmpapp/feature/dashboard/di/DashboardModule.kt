package com.jetbrains.kmpapp.feature.dashboard.di

import com.jetbrains.kmpapp.feature.dashboard.presentation.GyansarStudentDashboardViewModel
import com.jetbrains.kmpapp.feature.dashboard.presentation.GyansarTutorDashboardViewModel
import org.koin.dsl.module

val dashboardModule = module {
    factory { GyansarStudentDashboardViewModel(get(), get()) }
    factory { GyansarTutorDashboardViewModel(get()) }
}
