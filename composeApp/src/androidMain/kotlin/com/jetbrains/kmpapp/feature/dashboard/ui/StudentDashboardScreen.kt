package com.jetbrains.kmpapp.feature.dashboard.ui

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jetbrains.kmpapp.feature.dashboard.presentation.StudentDashboardState
import com.jetbrains.kmpapp.ui.components.AvatarChip
import com.jetbrains.kmpapp.ui.components.BottomNav
import com.jetbrains.kmpapp.ui.components.FloatingActionButton
import com.jetbrains.kmpapp.ui.components.PracticeCard
import com.jetbrains.kmpapp.ui.components.RecentTestCard
import com.jetbrains.kmpapp.ui.components.SectionLabel
import com.jetbrains.kmpapp.ui.components.StatCard

@Composable
fun StudentDashboardScreen(
    state: StudentDashboardState,
    onCreateTest: () -> Unit,
    onRecentTestClick: (Long) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = state.greeting,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = state.subtext,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                }
                AvatarChip(initials = state.initials)
            }
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
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
            Spacer(Modifier.height(16.dp))
            SectionLabel(state.practiceLabel)
            PracticeCard(
                title = state.practice.title,
                subtitle = state.practice.subtitle,
                cta = state.practice.cta
            )
            Spacer(Modifier.height(16.dp))
            SectionLabel(state.recentTestsLabel)
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                state.recentTests.forEach { test ->
                    val quizId = test.quizId
                    RecentTestCard(
                        subject = test.subject,
                        score = test.score,
                        date = test.date,
                        onClick = {
                            if (quizId != null) {
                                onRecentTestClick(quizId)
                            }
                        }
                    )
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 72.dp),
            onClick = onCreateTest
        )
        BottomNav(
            items = state.navItems,
            selectedIndex = state.selectedNavIndex,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
