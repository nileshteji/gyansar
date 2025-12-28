package com.jetbrains.kmpapp.feature.dashboard.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jetbrains.kmpapp.feature.dashboard.presentation.TutorDashboardState
import com.jetbrains.kmpapp.ui.components.AnnouncementItem
import com.jetbrains.kmpapp.ui.components.ClassCard
import com.jetbrains.kmpapp.ui.components.FloatingActionButton
import com.jetbrains.kmpapp.ui.components.SectionLabel

@Composable
fun TutorDashboardScreen(
    state: TutorDashboardState,
    onCreateQuiz: () -> Unit,
    onQuizSelected: (Long) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = state.title,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(16.dp))
            SectionLabel(state.classesLabel)
            state.classes.forEachIndexed { index, classInfo ->
                ClassCard(classInfo)
                if (index < state.classes.lastIndex) {
                    Spacer(Modifier.height(12.dp))
                }
            }
            Spacer(Modifier.height(16.dp))
            SectionLabel(state.announcementsLabel)
            state.announcements.forEach { announcement ->
                AnnouncementItem(text = announcement.text)
            }
            Spacer(Modifier.height(16.dp))
            SectionLabel(state.recentQuizzesLabel)
            if (state.recentQuizzes.isEmpty()) {
                Text(
                    text = "No quizzes yet. Create your first quiz.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    state.recentQuizzes.forEach { quiz ->
                        TutorQuizCard(
                            title = quiz.title,
                            subject = quiz.subject,
                            createdLabel = quiz.createdLabel,
                            onClick = { onQuizSelected(quiz.quizId) }
                        )
                    }
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 72.dp),
            onClick = onCreateQuiz
        )
    }
}

@Composable
private fun TutorQuizCard(
    title: String,
    subject: String,
    createdLabel: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Text(
            text = subject,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
        Spacer(Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = createdLabel,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Text(
                text = "View",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
