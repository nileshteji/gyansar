package com.jetbrains.kmpapp.feature.auth.presentation

data class LoginScreenState(
    val tagline: String,
    val buttonText: String,
    val termsText: String
)

data class ProfileSelectionState(
    val header: String,
    val options: List<ProfileOptionState>
)

data class ProfileOptionState(
    val title: String,
    val description: String,
    val badge: String
)
