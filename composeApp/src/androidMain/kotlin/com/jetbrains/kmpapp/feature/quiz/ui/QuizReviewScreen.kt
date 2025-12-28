package com.jetbrains.kmpapp.feature.quiz.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import com.jetbrains.kmpapp.feature.quiz.presentation.QuizReviewState
import com.jetbrains.kmpapp.ui.components.PrimaryButton
import com.jetbrains.kmpapp.ui.components.QuestionReviewCard
import com.jetbrains.kmpapp.ui.components.SecondaryButton

@Composable
fun QuizReviewScreen(
    state: QuizReviewState,
    onAddQuestion: () -> Unit,
    onFinalize: () -> Unit,
    onBack: () -> Unit = {}
) {
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
                text = state.title,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(Modifier.height(16.dp))
        state.questions.forEachIndexed { index, question ->
            QuestionReviewCard(
                question = question.question,
                answers = question.answers,
                tags = question.tags,
                editLabel = state.editLabel,
                deleteLabel = state.deleteLabel
            )
            if (index < state.questions.lastIndex) {
                Spacer(Modifier.height(12.dp))
            }
        }
        Spacer(Modifier.height(16.dp))
        SecondaryButton(text = state.addButtonText, onClick = onAddQuestion)
        Spacer(Modifier.height(12.dp))
        PrimaryButton(text = state.finalizeButtonText, onClick = onFinalize)
    }
}
