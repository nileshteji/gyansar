import SwiftUI
import Shared

struct QuizAnalyticsView: View {
    let quizId: Int64
    let onBack: () -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    @State private var selectedTabIndex: Int = 0
    
    private let state = GyansarWireframeData.shared.gallery.quizAnalytics
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                headerRow
                tabsSection
                metricsSection
                distributionSection
                toughestQuestionSection
            }
        }
        .background(theme.background)
        .navigationBarHidden(true)
    }
    
    private var headerRow: some View {
        HStack(spacing: 8) {
            Button(action: onBack) {
                Text("<-")
                    .font(GyansarTypography.titleLarge)
                    .foregroundColor(theme.tertiary)
            }
            Text(state.title)
                .font(GyansarTypography.titleMedium)
                .foregroundColor(theme.onBackground)
        }
    }
    
    private var tabsSection: some View {
        TabPillRowView(
            options: state.tabs as? [String] ?? ["Overview", "Question Analysis", "Students"],
            selectedIndex: selectedTabIndex,
            onSelect: { selectedTabIndex = $0 }
        )
    }
    
    private var metricsSection: some View {
        HStack(spacing: 12) {
            ForEach(Array((state.metrics as? [MetricState] ?? []).enumerated()), id: \.offset) { _, metric in
                MetricCardView(title: metric.title, value: metric.value)
            }
        }
    }
    
    private var distributionSection: some View {
        VStack(alignment: .leading, spacing: 12) {
            SectionLabelView(text: state.scoreDistributionLabel)
            HistogramView(
                bars: state.histogramBars as? [Float] ?? [0.3, 0.6, 0.45, 0.8, 0.5, 0.7],
                labels: state.histogramLabels as? [String] ?? ["40%", "60%", "80%", "100%"]
            )
        }
    }
    
    private var toughestQuestionSection: some View {
        VStack(alignment: .leading, spacing: 12) {
            SectionLabelView(text: state.toughestQuestionLabel)
            HighlightCardView(text: state.toughestQuestion)
        }
    }
}
