package com.jetbrains.kmpapp.feature.dashboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jetbrains.kmpapp.feature.dashboard.presentation.StudentIdentityState

@Composable
fun StudentIdentityScreen(
    state: StudentIdentityState,
    onIdChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Who is studying today?",
            style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = "Enter a student ID to personalize the dashboard and save activity locally.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.studentId,
            onValueChange = onIdChange,
            label = { Text("Student ID") },
            singleLine = true
        )
        Spacer(Modifier.height(12.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.displayName,
            onValueChange = onNameChange,
            label = { Text("Display name (optional)") },
            singleLine = true
        )
        state.error?.let {
            Spacer(Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelMedium)
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = onSave,
            enabled = !state.isSaving
        ) {
            Text(text = if (state.isSaving) "Saving..." else "Continue")
        }
    }
}
