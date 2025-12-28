package com.jetbrains.kmpapp.feature.wireframe.di

import com.jetbrains.kmpapp.feature.wireframe.presentation.GyansarWireframesViewModel
import org.koin.dsl.module

val wireframeModule = module {
    factory { GyansarWireframesViewModel() }
}
