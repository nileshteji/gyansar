import SwiftUI
import Shared

struct PerformanceAnalyticsView: View {
    let quizId: Int64
    let onBack: () -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    
    private let state = GyansarWireframeData.shared.gallery.performanceAnalytics
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                headerRow
                summaryCard
                performanceSection
                reviewSection
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
                .font(GyansarTypography.titleLarge)
                .foregroundColor(theme.onBackground)
        }
    }
    
    private var summaryCard: some View {
        SummaryCardView(
            score: state.score,
            accuracyLabel: state.accuracyLabel,
            accuracy: state.accuracy,
            timeLabel: state.timeLabel,
            time: state.time
        )
    }
    
    private var performanceSection: some View {
        VStack(alignment: .leading, spacing: 12) {
            SectionLabelView(text: state.performanceLabel)
            ForEach(Array((state.topicPerformance as? [TopicPerformanceState] ?? []).enumerated()), id: \.offset) { _, topic in
                TopicBarView(label: topic.label, percent: Int(topic.percent))
            }
        }
    }
    
    private var reviewSection: some View {
        VStack(alignment: .leading, spacing: 12) {
            SectionLabelView(text: state.reviewLabel)
            ReviewAnswerCardView(answer: state.reviewAnswer)
        }
    }
}

struct SummaryCardView: View {
    let score: String
    let accuracyLabel: String
    let accuracy: String
    let timeLabel: String
    let time: String
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        VStack(spacing: 12) {
            Text(score)
                .font(GyansarTypography.displayLarge)
                .foregroundColor(theme.primary)
            
            HStack(spacing: 24) {
                VStack {
                    Text(accuracyLabel)
                        .font(GyansarTypography.labelMedium)
                        .foregroundColor(theme.onBackground.opacity(0.7))
                    Text(accuracy)
                        .font(GyansarTypography.titleMedium)
                        .foregroundColor(theme.onBackground)
                }
                
                VStack {
                    Text(timeLabel)
                        .font(GyansarTypography.labelMedium)
                        .foregroundColor(theme.onBackground.opacity(0.7))
                    Text(time)
                        .font(GyansarTypography.titleMedium)
                        .foregroundColor(theme.onBackground)
                }
            }
        }
        .padding(20)
        .frame(maxWidth: .infinity)
        .background(theme.surface)
        .overlay(
            RoundedRectangle(cornerRadius: 18)
                .stroke(theme.outline, lineWidth: 1)
        )
        .cornerRadius(18)
    }
}

struct TopicBarView: View {
    let label: String
    let percent: Int
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            HStack {
                Text(label)
                    .font(GyansarTypography.labelMedium)
                    .foregroundColor(theme.onBackground)
                Spacer()
                Text("\(percent)%")
                    .font(GyansarTypography.labelMedium)
                    .foregroundColor(theme.onBackground.opacity(0.7))
            }
            
            GeometryReader { geometry in
                ZStack(alignment: .leading) {
                    RoundedRectangle(cornerRadius: 4)
                        .fill(theme.surfaceVariant)
                        .frame(height: 8)
                    
                    RoundedRectangle(cornerRadius: 4)
                        .fill(theme.primary)
                        .frame(width: geometry.size.width * CGFloat(percent) / 100, height: 8)
                }
            }
            .frame(height: 8)
        }
    }
}

struct ReviewAnswerCardView: View {
    let answer: ReviewAnswerState
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            Text(answer.question)
                .font(GyansarTypography.bodyMedium)
                .foregroundColor(theme.onSurface)
            
            HStack(spacing: 6) {
                Circle()
                    .fill(theme.error)
                    .frame(width: 10, height: 10)
                Text("\(answer.wrongLabel): \(answer.wrongAnswer)")
                    .font(GyansarTypography.labelMedium)
                    .foregroundColor(theme.onBackground)
            }
            
            HStack(spacing: 6) {
                Circle()
                    .fill(theme.primary)
                    .frame(width: 10, height: 10)
                Text("\(answer.correctLabel): \(answer.correctAnswer)")
                    .font(GyansarTypography.labelMedium)
                    .foregroundColor(theme.onBackground)
            }
            
            SecondaryButtonView(text: answer.cta, action: {})
        }
        .padding(14)
        .background(theme.surface)
        .overlay(
            RoundedRectangle(cornerRadius: 16)
                .stroke(theme.outline, lineWidth: 1)
        )
        .cornerRadius(16)
    }
}
