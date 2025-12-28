package com.jetbrains.kmpapp.feature.assessment.di

import com.jetbrains.kmpapp.feature.assessment.presentation.GyansarAssessmentViewModel
import org.koin.dsl.module

val assessmentModule = module {
    factory { GyansarAssessmentViewModel(get()) }
}
