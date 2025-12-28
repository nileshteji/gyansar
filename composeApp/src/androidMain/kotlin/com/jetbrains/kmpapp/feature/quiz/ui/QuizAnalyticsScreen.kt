package com.jetbrains.kmpapp.feature.quiz.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.clickable
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
import com.jetbrains.kmpapp.feature.quiz.presentation.QuizAnalyticsState
import com.jetbrains.kmpapp.ui.components.HighlightCard
import com.jetbrains.kmpapp.ui.components.Histogram
import com.jetbrains.kmpapp.ui.components.MetricCard
import com.jetbrains.kmpapp.ui.components.SectionLabel
import com.jetbrains.kmpapp.ui.components.TabPillRow

@Composable
fun QuizAnalyticsScreen(
    state: QuizAnalyticsState,
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
        TabPillRow(options = state.tabs, selectedIndex = state.selectedTabIndex)
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            state.metrics.forEach { metric ->
                MetricCard(
                    title = metric.title,
                    value = metric.value,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        SectionLabel(state.scoreDistributionLabel)
        Histogram(bars = state.histogramBars, labels = state.histogramLabels)
        Spacer(Modifier.height(16.dp))
        SectionLabel(state.toughestQuestionLabel)
        HighlightCard(text = state.toughestQuestion)
    }
}
