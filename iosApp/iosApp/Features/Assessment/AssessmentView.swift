import SwiftUI
import Shared

struct AssessmentView: View {
    let quizId: Int64
    let onNext: () -> Void
    let onPrevious: () -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    @StateObject private var viewModel = AssessmentViewModel()
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                headerRow
                questionSection
                answersSection
                navigationRow
            }
        }
        .background(theme.background)
        .navigationBarHidden(true)
        .onAppear {
            viewModel.setQuizId(quizId)
        }
    }
    
    private var headerRow: some View {
        HStack {
            Text(viewModel.state.progressLabel)
                .font(GyansarTypography.labelLarge)
                .foregroundColor(theme.onBackground)
            
            Spacer()
            
            Text(viewModel.state.timerLabel)
                .font(GyansarTypography.labelLarge)
                .foregroundColor(theme.secondary)
            
            Spacer()
            
            Text(viewModel.state.flagLabel)
                .font(GyansarTypography.labelLarge)
                .foregroundColor(theme.tertiary)
        }
    }
    
    private var questionSection: some View {
        Text(viewModel.state.questionText)
            .font(GyansarTypography.bodyLarge)
            .foregroundColor(theme.onBackground)
    }
    
    private var answersSection: some View {
        VStack(spacing: 12) {
            let answers = viewModel.state.answers as? [AnswerOptionState] ?? []
            ForEach(Array(answers.enumerated()), id: \.offset) { index, answer in
                AnswerOptionView(
                    text: answer.text,
                    selected: answer.selected,
                    action: { viewModel.selectAnswer(index: Int32(index)) }
                )
            }
        }
    }
    
    private var navigationRow: some View {
        HStack {
            Button(action: {
                viewModel.previous()
                onPrevious()
            }) {
                Text(viewModel.state.previousLabel)
                    .font(GyansarTypography.labelLarge)
                    .foregroundColor(theme.tertiary)
            }
            
            Spacer()
            
            HintPillView(text: viewModel.state.hintLabel)
            
            Spacer()
            
            Button(action: onNext) {
                Text(viewModel.state.nextLabel)
                    .font(GyansarTypography.labelLarge)
                    .foregroundColor(theme.tertiary)
            }
        }
    }
}

@MainActor
class AssessmentViewModel: ObservableObject {
    @Published var state: AssessmentState
    
    private var sharedViewModel: GyansarAssessmentViewModel?
    
    init() {
        state = GyansarWireframeData.shared.gallery.assessment
    }
    
    func setQuizId(_ quizId: Int64) {
        sharedViewModel = KoinDependencies.shared.assessmentViewModel
        sharedViewModel?.setQuizId(quizId: quizId)
    }
    
    func selectAnswer(index: Int32) {
        sharedViewModel?.selectAnswer(index: index)
    }
    
    func previous() {
        sharedViewModel?.previous()
    }
}
