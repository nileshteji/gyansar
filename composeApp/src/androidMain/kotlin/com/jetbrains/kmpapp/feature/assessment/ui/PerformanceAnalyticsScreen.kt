package com.jetbrains.kmpapp.feature.assessment.ui

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
import com.jetbrains.kmpapp.feature.assessment.presentation.PerformanceAnalyticsState
import com.jetbrains.kmpapp.ui.components.ReviewAnswerCard
import com.jetbrains.kmpapp.ui.components.SectionLabel
import com.jetbrains.kmpapp.ui.components.SummaryCard
import com.jetbrains.kmpapp.ui.components.TopicBar

@Composable
fun PerformanceAnalyticsScreen(
    state: PerformanceAnalyticsState,
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
                style = MaterialTheme.typography.titleLarge
            )
        }
        Spacer(Modifier.height(16.dp))
        SummaryCard(
            score = state.score,
            accuracyLabel = state.accuracyLabel,
            accuracy = state.accuracy,
            timeLabel = state.timeLabel,
            time = state.time
        )
        Spacer(Modifier.height(16.dp))
        SectionLabel(state.performanceLabel)
        state.topicPerformance.forEach { topic ->
            TopicBar(label = topic.label, percent = topic.percent)
        }
        Spacer(Modifier.height(16.dp))
        SectionLabel(state.reviewLabel)
        ReviewAnswerCard(state.reviewAnswer)
    }
}
