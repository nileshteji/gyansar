package com.jetbrains.kmpapp.feature.wireframe.presentation

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GyansarWireframesViewModel : ViewModel() {
    private val galleryFlow = MutableStateFlow(GyansarWireframeData.gallery)

    @NativeCoroutinesState
    val gallery: StateFlow<GyansarWireframeGalleryState> = galleryFlow
}
