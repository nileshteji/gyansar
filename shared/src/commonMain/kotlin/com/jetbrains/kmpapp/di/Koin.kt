package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.feature.assessment.di.assessmentModule
import com.jetbrains.kmpapp.feature.dashboard.di.dashboardModule
import com.jetbrains.kmpapp.feature.museum.di.museumModule
import com.jetbrains.kmpapp.feature.quiz.di.quizModule
import com.jetbrains.kmpapp.feature.wireframe.di.wireframeModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin() = initKoin(emptyList())

fun initKoin(extraModules: List<Module>) {
    startKoin {
        modules(
            museumModule,
            quizModule,
            assessmentModule,
            dashboardModule,
            wireframeModule,
            platformModule(),
            *extraModules.toTypedArray(),
        )
    }
}
