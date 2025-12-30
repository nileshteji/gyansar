package com.jetbrains.kmpapp.feature.dashboard.presentation

data class StudentIdentityState(
    val studentId: String = "",
    val displayName: String = "",
    val error: String? = null,
    val isSaving: Boolean = false
)
