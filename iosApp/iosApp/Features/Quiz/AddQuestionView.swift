import SwiftUI
import Shared

struct AddQuestionView: View {
    let quizId: Int64
    let onBack: () -> Void
    let onSave: () -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    
    @State private var prompt: String = ""
    @State private var optionA: String = ""
    @State private var optionB: String = ""
    @State private var optionC: String = ""
    @State private var optionD: String = ""
    @State private var correctAnswer: String = ""
    @State private var selectedDifficulty: Int = 1
    @State private var selectedBloom: Int = 1
    
    private let difficulties = ["Easy", "Medium", "Hard"]
    private let bloomLevels = ["Recall", "Understanding", "Applying"]
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 12) {
                headerRow
                promptField
                optionsSection
                correctAnswerField
                difficultySection
                bloomSection
                saveButton
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
            Text("Add Manual Question")
                .font(GyansarTypography.titleLarge)
                .foregroundColor(theme.onBackground)
        }
    }
    
    private var promptField: some View {
        VStack(alignment: .leading, spacing: 8) {
            SectionLabelView(text: "Question Prompt")
            TextField("", text: $prompt, axis: .vertical)
                .textFieldStyle(.roundedBorder)
                .lineLimit(3...6)
        }
    }
    
    private var optionsSection: some View {
        VStack(alignment: .leading, spacing: 8) {
            optionField(label: "Option A", text: $optionA)
            optionField(label: "Option B", text: $optionB)
            optionField(label: "Option C", text: $optionC)
            optionField(label: "Option D", text: $optionD)
        }
    }
    
    private func optionField(label: String, text: Binding<String>) -> some View {
        VStack(alignment: .leading, spacing: 4) {
            SectionLabelView(text: label)
            TextField("", text: text)
                .textFieldStyle(.roundedBorder)
        }
    }
    
    private var correctAnswerField: some View {
        VStack(alignment: .leading, spacing: 8) {
            SectionLabelView(text: "Correct Answer")
            TextField("", text: $correctAnswer)
                .textFieldStyle(.roundedBorder)
        }
    }
    
    private var difficultySection: some View {
        VStack(alignment: .leading, spacing: 8) {
            SectionLabelView(text: "Difficulty")
            SegmentedControlView(options: difficulties, selectedIndex: $selectedDifficulty)
        }
    }
    
    private var bloomSection: some View {
        VStack(alignment: .leading, spacing: 8) {
            SectionLabelView(text: "Bloom's Level")
            SegmentedControlView(options: bloomLevels, selectedIndex: $selectedBloom)
        }
    }
    
    private var saveButton: some View {
        PrimaryButtonView(text: "Save Question") {
            let options = [optionA, optionB, optionC, optionD].filter { !$0.isEmpty }
            if !prompt.isEmpty && !options.isEmpty {
                let draft = QuestionDraft(
                    prompt: prompt.trimmingCharacters(in: .whitespaces),
                    options: options,
                    correctAnswer: correctAnswer.isEmpty ? options.first ?? "" : correctAnswer,
                    difficulty: difficulties[selectedDifficulty],
                    bloomLevel: bloomLevels[selectedBloom]
                )
                onSave()
            }
        }
    }
}
