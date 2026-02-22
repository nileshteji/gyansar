import SwiftUI
import Shared

struct StatCardView: View {
    let title: String
    let value: String
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(value)
                .font(GyansarTypography.titleLarge)
                .foregroundColor(theme.onSurface)
            Text(title)
                .font(GyansarTypography.labelMedium)
                .foregroundColor(theme.onBackground.opacity(0.7))
        }
        .padding(12)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(theme.surface)
        .overlay(
            RoundedRectangle(cornerRadius: 16)
                .stroke(theme.outline, lineWidth: 1)
        )
        .cornerRadius(16)
    }
}

struct PracticeCardView: View {
    let title: String
    let subtitle: String
    let cta: String
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: 2) {
                Text(title)
                    .font(GyansarTypography.bodyMedium)
                    .foregroundColor(theme.onSurface)
                Text(subtitle)
                    .font(GyansarTypography.labelMedium)
                    .foregroundColor(theme.onBackground.opacity(0.6))
            }
            Spacer()
            Text(cta)
                .font(GyansarTypography.titleLarge)
                .foregroundColor(theme.secondary)
        }
        .padding(14)
        .background(theme.surfaceVariant)
        .overlay(
            RoundedRectangle(cornerRadius: 18)
                .stroke(theme.outline, lineWidth: 1)
        )
        .cornerRadius(18)
    }
}

struct RecentTestCardView: View {
    let subject: String
    let score: String
    let date: String
    let action: () -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        Button(action: action) {
            VStack(alignment: .leading, spacing: 4) {
                Text(subject)
                    .font(GyansarTypography.titleMedium)
                    .foregroundColor(theme.onSurface)
                Text(score)
                    .font(GyansarTypography.titleLarge)
                    .foregroundColor(theme.primary)
                Text(date)
                    .font(GyansarTypography.labelMedium)
                    .foregroundColor(theme.onBackground.opacity(0.6))
            }
            .padding(12)
            .background(theme.surface)
            .overlay(
                RoundedRectangle(cornerRadius: 16)
                    .stroke(theme.outline, lineWidth: 1)
            )
            .cornerRadius(16)
        }
        .buttonStyle(.plain)
    }
}

struct ClassCardView: View {
    let title: String
    let students: String
    let activityLabel: String
    let activity: String
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            Text(title)
                .font(GyansarTypography.titleMedium)
                .foregroundColor(theme.onSurface)
            Text(students)
                .font(GyansarTypography.labelMedium)
                .foregroundColor(theme.onBackground.opacity(0.6))
            Text("\(activityLabel): \(activity)")
                .font(GyansarTypography.bodyMedium)
                .foregroundColor(theme.onSurface)
        }
        .padding(14)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(theme.surface)
        .overlay(
            RoundedRectangle(cornerRadius: 18)
                .stroke(theme.outline, lineWidth: 1)
        )
        .cornerRadius(18)
    }
}

struct TutorQuizCardView: View {
    let title: String
    let subject: String
    let createdLabel: String
    let action: () -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        Button(action: action) {
            VStack(alignment: .leading, spacing: 6) {
                Text(title)
                    .font(GyansarTypography.titleMedium)
                    .foregroundColor(theme.onSurface)
                Text(subject)
                    .font(GyansarTypography.bodyMedium)
                    .foregroundColor(theme.onBackground.opacity(0.7))
                HStack {
                    Text(createdLabel)
                        .font(GyansarTypography.labelMedium)
                        .foregroundColor(theme.onBackground.opacity(0.6))
                    Spacer()
                    Text("View")
                        .font(GyansarTypography.labelMedium)
                        .foregroundColor(theme.primary)
                }
            }
            .padding(14)
            .frame(maxWidth: .infinity, alignment: .leading)
        }
        .buttonStyle(.plain)
    }
}

struct QuestionReviewCardView: View {
    let question: String
    let answers: String
    let tags: [String]
    let editLabel: String
    let deleteLabel: String
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(question)
                .font(GyansarTypography.bodyMedium)
                .foregroundColor(theme.onSurface)
            Text(answers)
                .font(GyansarTypography.labelMedium)
                .foregroundColor(theme.onBackground.opacity(0.7))
            HStack(spacing: 6) {
                ForEach(tags, id: \.self) { tag in
                    TagChipView(text: tag)
                }
                Spacer()
                Text(editLabel)
                    .font(GyansarTypography.labelMedium)
                    .foregroundColor(theme.tertiary)
                Text(deleteLabel)
                    .font(GyansarTypography.labelMedium)
                    .foregroundColor(theme.secondary)
            }
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

struct MetricCardView: View {
    let title: String
    let value: String
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(value)
                .font(GyansarTypography.titleLarge)
                .foregroundColor(theme.primary)
            Text(title)
                .font(GyansarTypography.labelMedium)
                .foregroundColor(theme.onBackground.opacity(0.7))
        }
        .padding(12)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(theme.surface)
        .overlay(
            RoundedRectangle(cornerRadius: 16)
                .stroke(theme.outline, lineWidth: 1)
        )
        .cornerRadius(16)
    }
}

struct HighlightCardView: View {
    let text: String
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        Text(text)
            .font(GyansarTypography.bodyMedium)
            .foregroundColor(theme.onSurface)
            .padding(14)
            .frame(maxWidth: .infinity, alignment: .leading)
            .background(theme.surfaceVariant)
            .overlay(
                RoundedRectangle(cornerRadius: 16)
                    .stroke(theme.outline, lineWidth: 1)
            )
            .cornerRadius(16)
    }
}
