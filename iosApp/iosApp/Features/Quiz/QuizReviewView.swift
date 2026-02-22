import SwiftUI
import Shared

struct QuizReviewView: View {
    let quizId: Int64
    let onAddQuestion: () -> Void
    let onFinalize: () -> Void
    let onBack: () -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    @StateObject private var viewModel = QuizReviewViewModel()
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                headerRow
                questionsSection
                actionButtons
            }
        }
        .background(theme.background)
        .navigationBarHidden(true)
        .onAppear {
            viewModel.setQuizId(quizId)
        }
    }
    
    private var headerRow: some View {
        HStack(spacing: 8) {
            Button(action: onBack) {
                Text("<-")
                    .font(GyansarTypography.titleLarge)
                    .foregroundColor(theme.tertiary)
            }
            Text(viewModel.state.title)
                .font(GyansarTypography.titleMedium)
                .foregroundColor(theme.onBackground)
        }
    }
    
    private var questionsSection: some View {
        VStack(spacing: 12) {
            let questions = viewModel.state.questions as? [QuizQuestionState] ?? []
            ForEach(Array(questions.enumerated()), id: \.offset) { _, question in
                QuestionReviewCardView(
                    question: question.question,
                    answers: question.answers,
                    tags: question.tags as? [String] ?? [],
                    editLabel: viewModel.state.editLabel,
                    deleteLabel: viewModel.state.deleteLabel
                )
            }
        }
    }
    
    private var actionButtons: some View {
        VStack(spacing: 12) {
            SecondaryButtonView(text: viewModel.state.addButtonText, action: onAddQuestion)
            PrimaryButtonView(text: viewModel.state.finalizeButtonText, action: onFinalize)
        }
    }
}

@MainActor
class QuizReviewViewModel: ObservableObject {
    @Published var state: QuizReviewState
    
    private var sharedViewModel: GyansarQuizDetailViewModel?
    
    init() {
        state = GyansarWireframeData.shared.gallery.quizReview
    }
    
    func setQuizId(_ quizId: Int64) {
        sharedViewModel = KoinDependencies.shared.quizDetailViewModel
        sharedViewModel?.setQuizId(quizId: quizId)
    }
}
