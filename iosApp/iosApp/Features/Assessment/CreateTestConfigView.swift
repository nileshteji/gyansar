import SwiftUI
import Shared

struct CreateTestConfigView: View {
    let onGenerateTest: (Int64) -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    @EnvironmentObject private var navigator: GyansarNavigatorState
    
    @State private var questionCount: Double = 20
    @State private var timeLimit: String = "30"
    @State private var selectedQuestionType: Int = 0
    @State private var selectedDifficulty: Int = 1
    
    private let state = GyansarWireframeData.shared.gallery.createTest
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                headerRow
                documentSection
                questionTypeSection
                difficultySection
                questionCountSection
                timeLimitSection
                generateButton
            }
        }
        .background(theme.background)
        .navigationBarHidden(true)
    }
    
    private var headerRow: some View {
        HStack(spacing: 8) {
            Button(action: { navigator.popBack() }) {
                Text("<-")
                    .font(GyansarTypography.titleLarge)
                    .foregroundColor(theme.tertiary)
            }
            Text(state.title)
                .font(GyansarTypography.titleLarge)
                .foregroundColor(theme.onBackground)
        }
    }
    
    private var documentSection: some View {
        Text(state.documentName)
            .font(GyansarTypography.bodyMedium)
            .foregroundColor(theme.onBackground.opacity(0.7))
    }
    
    private var questionTypeSection: some View {
        VStack(alignment: .leading, spacing: 8) {
            SectionLabelView(text: state.questionTypeLabel)
            SegmentedControlView(
                options: state.questionTypes as? [String] ?? ["MCQ", "Long Answer"],
                selectedIndex: $selectedQuestionType
            )
        }
    }
    
    private var difficultySection: some View {
        VStack(alignment: .leading, spacing: 8) {
            SectionLabelView(text: state.difficultyLabel)
            SegmentedControlView(
                options: state.difficultyLevels as? [String] ?? ["Easy", "Medium", "Hard"],
                selectedIndex: $selectedDifficulty
            )
        }
    }
    
    private var questionCountSection: some View {
        VStack(alignment: .leading, spacing: 8) {
            HStack {
                SectionLabelView(text: state.questionCountLabel)
                Spacer()
                Text("\(Int(questionCount))")
                    .font(GyansarTypography.titleMedium)
                    .foregroundColor(theme.onBackground)
            }
            Slider(
                value: $questionCount,
                in: Double(state.questionRange.first)...Double(state.questionRange.last),
                step: 1
            )
            .accentColor(theme.primary)
        }
    }
    
    private var timeLimitSection: some View {
        VStack(alignment: .leading, spacing: 8) {
            SectionLabelView(text: state.timeLimitLabel)
            TextField("", text: $timeLimit)
                .textFieldStyle(.roundedBorder)
                .keyboardType(.numberPad)
        }
    }
    
    private var generateButton: some View {
        PrimaryButtonView(text: state.generateButtonText) {
            onGenerateTest(1)
        }
    }
}

struct SegmentedControlView: View {
    let options: [String]
    @Binding var selectedIndex: Int
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        HStack(spacing: 0) {
            ForEach(Array(options.enumerated()), id: \.offset) { index, option in
                let selected = index == selectedIndex
                let background = selected ? theme.primary : theme.surfaceVariant
                let textColor = selected ? theme.onPrimary : theme.onSurfaceVariant
                
                Button(action: { selectedIndex = index }) {
                    Text(option)
                        .font(GyansarTypography.labelMedium)
                        .foregroundColor(textColor)
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 10)
                        .background(background)
                }
            }
        }
        .cornerRadius(12)
        .overlay(
            RoundedRectangle(cornerRadius: 12)
                .stroke(theme.outline, lineWidth: 1)
        )
    }
}
