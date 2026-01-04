package com.jetbrains.kmpapp.feature.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jetbrains.kmpapp.feature.dashboard.presentation.FlashcardCardState
import com.jetbrains.kmpapp.feature.dashboard.presentation.RecentTestState
import com.jetbrains.kmpapp.feature.dashboard.presentation.StudentDashboardState
import com.jetbrains.kmpapp.ui.components.AvatarChip
import com.jetbrains.kmpapp.ui.components.BottomNav
import com.jetbrains.kmpapp.ui.components.SectionLabel
import com.jetbrains.kmpapp.ui.components.StatCard

@Composable
fun StudentDashboardScreen(
    state: StudentDashboardState,
    onUpdateTitle: (String) -> Unit,
    onUpdateSubject: (String) -> Unit,
    onUpdateTopics: (String) -> Unit,
    onUpdateQuestionCount: (String) -> Unit,
    onUpdateTimeLimit: (String) -> Unit,
    onToggleSmartSelection: (Boolean) -> Unit,
    onToggleInstantFeedback: (Boolean) -> Unit,
    onCreateAiTest: (String) -> Unit,
    onRecentTestClick: (Long?) -> Unit,
    onAddStudent: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HeroBanner(state = state, onAddStudent = onAddStudent)
            Spacer(Modifier.height(12.dp))
            StatRow(state)
            Spacer(Modifier.height(12.dp))
            AiTestBuilder(
                state = state,
                onUpdateTitle = onUpdateTitle,
                onUpdateSubject = onUpdateSubject,
                onUpdateTopics = onUpdateTopics,
                onUpdateQuestionCount = onUpdateQuestionCount,
                onUpdateTimeLimit = onUpdateTimeLimit,
                onToggleSmartSelection = onToggleSmartSelection,
                onToggleInstantFeedback = onToggleInstantFeedback,
                onCreateAiTest = { onCreateAiTest(state.studentId) }
            )
            Spacer(Modifier.height(16.dp))
            ActivityLogSection(
                title = state.recentTestsLabel,
                items = state.recentTests,
                onItemClick = onRecentTestClick
            )
            Spacer(Modifier.height(16.dp))
            FlashcardRail(
                label = state.flashcardLabel,
                cards = state.flashcards
            )
            Spacer(Modifier.height(16.dp))
            GamificationCard(state = state)
            Spacer(Modifier.height(24.dp))
        }
        BottomNav(
            items = state.navItems,
            selectedIndex = state.selectedNavIndex,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun HeroBanner(state: StudentDashboardState, onAddStudent: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(text = state.greeting, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = state.subtext,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatusPill(text = if (state.studentId.isBlank()) "No ID" else "ID: ${state.studentId}")
                    StatusPill(text = state.streakValue, tone = MaterialTheme.colorScheme.primary.copy(alpha = 0.16f))
                }
                if (state.isStudentMissing) {
                    Button(
                        onClick = onAddStudent,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(text = "Enter Student ID", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
            AvatarChip(initials = state.initials)
        }
    }
}

@Composable
private fun StatRow(state: StudentDashboardState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        state.stats.forEach { stat ->
            StatCard(
                title = stat.title,
                value = stat.value,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun AiTestBuilder(
    state: StudentDashboardState,
    onUpdateTitle: (String) -> Unit,
    onUpdateSubject: (String) -> Unit,
    onUpdateTopics: (String) -> Unit,
    onUpdateQuestionCount: (String) -> Unit,
    onUpdateTimeLimit: (String) -> Unit,
    onToggleSmartSelection: (Boolean) -> Unit,
    onToggleInstantFeedback: (Boolean) -> Unit,
    onCreateAiTest: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SectionLabel("Build your AI test")
        TextField(
            value = state.aiTestForm.title,
            onValueChange = onUpdateTitle,
            label = { Text("Test title") },
            singleLine = true
        )
        TextField(
            value = state.aiTestForm.subject,
            onValueChange = onUpdateSubject,
            label = { Text("Subject") },
            singleLine = true
        )
        TextField(
            value = state.aiTestForm.topics,
            onValueChange = onUpdateTopics,
            label = { Text("Topics (comma separated)") },
            singleLine = false,
            minLines = 2
        )
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            TextField(
                modifier = Modifier.weight(1f),
                value = state.aiTestForm.questionCount,
                onValueChange = onUpdateQuestionCount,
                label = { Text("Number of questions") },
                singleLine = true
            )
            TextField(
                modifier = Modifier.weight(1f),
                value = state.aiTestForm.timeLimitMinutes,
                onValueChange = onUpdateTimeLimit,
                label = { Text("Time limit (min)") },
                singleLine = true
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Smart question selection", style = MaterialTheme.typography.bodyLarge)
            Switch(checked = state.aiTestForm.smartSelectionEnabled, onCheckedChange = onToggleSmartSelection)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Instant feedback mode", style = MaterialTheme.typography.bodyLarge)
            Switch(checked = state.aiTestForm.instantFeedbackEnabled, onCheckedChange = onToggleInstantFeedback)
        }
        Button(
            onClick = onCreateAiTest,
            enabled = state.studentId.isNotBlank(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = state.aiTestForm.submitLabel, color = MaterialTheme.colorScheme.onPrimary)
        }
        state.aiTestForm.statusMessage?.let {
            Text(text = it, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
        }
    }
}

@Composable
private fun ActivityLogSection(
    title: String,
    items: List<RecentTestState>,
    onItemClick: (Long?) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        SectionLabel(title)
        Spacer(Modifier.height(8.dp))
        items.forEach { test ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp),
                onClick = { onItemClick(test.quizId) }
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = test.title.ifBlank { test.subject }, style = MaterialTheme.typography.titleMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        StatusPill(text = test.score, tone = MaterialTheme.colorScheme.primary.copy(alpha = 0.16f))
                        StatusPill(text = test.date)
                        test.tags.forEach { tag -> StatusPill(text = tag) }
                    }
                }
            }
        }
    }
}

@Composable
private fun FlashcardRail(label: String, cards: List<FlashcardCardState>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        SectionLabel(label)
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            cards.forEach { card -> FlashcardCard(card) }
        }
    }
}

@Composable
private fun FlashcardCard(card: FlashcardCardState) {
    Card(
        modifier = Modifier.width(220.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = card.topic.uppercase(), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.tertiary)
            Text(
                text = card.prompt,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                ProgressDot(color = MaterialTheme.colorScheme.primary)
                Text(text = "Mastery ${card.mastery}%", style = MaterialTheme.typography.labelMedium)
                if (card.isStarred) {
                    ProgressDot(color = MaterialTheme.colorScheme.secondary)
                }
            }
        }
    }
}

@Composable
private fun GamificationCard(state: StudentDashboardState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            SectionLabel("XP & Badges")
            Text(text = state.gamification.level, style = MaterialTheme.typography.titleMedium)
            Text(text = state.gamification.xp, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                state.gamification.badges.forEach { badge -> StatusPill(text = badge) }
            }
        }
    }
}

@Composable
private fun StatusPill(text: String, tone: Color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)) {
    Box(
        modifier = Modifier
            .background(tone, RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
private fun ProgressDot(color: Color) {
    Box(
        modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(color)
    )
}
