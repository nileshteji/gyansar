package com.jetbrains.kmpapp.feature.assessment.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jetbrains.kmpapp.feature.assessment.presentation.CreateTestConfigState
import com.jetbrains.kmpapp.ui.components.PrimaryButton
import com.jetbrains.kmpapp.ui.components.SectionLabel
import com.jetbrains.kmpapp.ui.components.SegmentedControl

@Composable
fun CreateTestConfigScreen(
    state: CreateTestConfigState,
    onGenerateTest: () -> Unit
) {
    var questionCount by remember { mutableStateOf(state.defaultQuestionCount.toFloat()) }
    var timeLimit by remember { mutableStateOf(state.timeLimitMinutes) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "<-",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.tertiary
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = state.title,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Text(
            text = state.documentName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
        Spacer(Modifier.height(16.dp))
        SectionLabel(state.questionTypeLabel)
        SegmentedControl(
            options = state.questionTypes,
            selectedIndex = state.selectedQuestionType
        )
        Spacer(Modifier.height(16.dp))
        SectionLabel(state.difficultyLabel)
        SegmentedControl(
            options = state.difficultyLevels,
            selectedIndex = state.selectedDifficulty
        )
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SectionLabel(state.questionCountLabel)
            Text(
                text = questionCount.toInt().toString(),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Slider(
            value = questionCount,
            onValueChange = { questionCount = it },
            valueRange = state.questionRange.start.toFloat()..state.questionRange.endInclusive.toFloat()
        )
        Spacer(Modifier.height(16.dp))
        SectionLabel(state.timeLimitLabel)
        OutlinedTextField(
            value = timeLimit,
            onValueChange = { timeLimit = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(Modifier.height(20.dp))
        PrimaryButton(text = state.generateButtonText, onClick = onGenerateTest)
    }
}
