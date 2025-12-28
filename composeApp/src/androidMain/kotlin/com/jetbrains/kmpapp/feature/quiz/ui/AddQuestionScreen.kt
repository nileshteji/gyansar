package com.jetbrains.kmpapp.feature.quiz.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jetbrains.kmpapp.feature.quiz.domain.QuestionDraft
import com.jetbrains.kmpapp.ui.components.PrimaryButton
import com.jetbrains.kmpapp.ui.components.SectionLabel
import com.jetbrains.kmpapp.ui.components.SegmentedControl

@Composable
fun AddQuestionScreen(
    onBack: () -> Unit,
    onSave: (QuestionDraft) -> Unit
) {
    var prompt by remember { mutableStateOf("") }
    var optionA by remember { mutableStateOf("") }
    var optionB by remember { mutableStateOf("") }
    var optionC by remember { mutableStateOf("") }
    var optionD by remember { mutableStateOf("") }
    var correctAnswer by remember { mutableStateOf("") }
    val difficulties = listOf("Easy", "Medium", "Hard")
    val bloomLevels = listOf("Recall", "Understanding", "Applying")
    var selectedDifficulty by remember { mutableStateOf(1) }
    var selectedBloom by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "<-",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.clickable(onClick = onBack)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Add Manual Question",
                style = MaterialTheme.typography.titleLarge
            )
        }
        Spacer(Modifier.height(16.dp))
        SectionLabel("Question Prompt")
        OutlinedTextField(
            value = prompt,
            onValueChange = { prompt = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        SectionLabel("Option A")
        OutlinedTextField(
            value = optionA,
            onValueChange = { optionA = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(Modifier.height(8.dp))
        SectionLabel("Option B")
        OutlinedTextField(
            value = optionB,
            onValueChange = { optionB = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(Modifier.height(8.dp))
        SectionLabel("Option C")
        OutlinedTextField(
            value = optionC,
            onValueChange = { optionC = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(Modifier.height(8.dp))
        SectionLabel("Option D")
        OutlinedTextField(
            value = optionD,
            onValueChange = { optionD = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(Modifier.height(12.dp))
        SectionLabel("Correct Answer")
        OutlinedTextField(
            value = correctAnswer,
            onValueChange = { correctAnswer = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(Modifier.height(12.dp))
        SectionLabel("Difficulty")
        SegmentedControl(
            options = difficulties,
            selectedIndex = selectedDifficulty,
            onSelect = { selectedDifficulty = it }
        )
        Spacer(Modifier.height(12.dp))
        SectionLabel("Bloom's Level")
        SegmentedControl(
            options = bloomLevels,
            selectedIndex = selectedBloom,
            onSelect = { selectedBloom = it }
        )
        Spacer(Modifier.height(20.dp))
        PrimaryButton(
            text = "Save Question",
            onClick = {
                val options = listOf(optionA, optionB, optionC, optionD).filter { it.isNotBlank() }
                if (prompt.isNotBlank() && options.isNotEmpty()) {
                    onSave(
                        QuestionDraft(
                            prompt = prompt.trim(),
                            options = options,
                            correctAnswer = correctAnswer.ifBlank { options.first() },
                            difficulty = difficulties[selectedDifficulty],
                            bloomLevel = bloomLevels[selectedBloom]
                        )
                    )
                }
            }
        )
    }
}
