package com.jetbrains.kmpapp.feature.wireframe.presentation

import com.rickclephas.kmp.observableviewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GyansarWireframesViewModel : ViewModel() {
    private val galleryFlow = MutableStateFlow(GyansarWireframeData.gallery)

    val gallery: StateFlow<GyansarWireframeGalleryState> = galleryFlow
}
