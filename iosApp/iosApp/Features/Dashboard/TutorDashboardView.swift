import SwiftUI
import Shared

struct TutorDashboardView: View {
    let onCreateQuiz: () -> Void
    let onQuizSelected: (Int64) -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    @StateObject private var viewModel = TutorDashboardViewModel()
    
    var body: some View {
        ZStack(alignment: .bottomTrailing) {
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    titleSection
                    classesSection
                    announcementsSection
                    quizzesSection
                }
                .padding(.bottom, 80)
            }
            .background(theme.background)
            
            FABView(action: onCreateQuiz)
                .padding(.trailing, 16)
                .padding(.bottom, 72)
        }
        .navigationBarHidden(true)
    }
    
    private var titleSection: some View {
        Text(viewModel.state.title)
            .font(GyansarTypography.titleLarge)
            .foregroundColor(theme.onBackground)
    }
    
    private var classesSection: some View {
        VStack(alignment: .leading, spacing: 12) {
            SectionLabelView(text: viewModel.state.classesLabel)
            ForEach(Array((viewModel.state.classes as? [ClassSummaryState] ?? []).enumerated()), id: \.offset) { _, classInfo in
                ClassCardView(
                    title: classInfo.title,
                    students: classInfo.students,
                    activityLabel: classInfo.activityLabel,
                    activity: classInfo.activity
                )
            }
        }
    }
    
    private var announcementsSection: some View {
        VStack(alignment: .leading, spacing: 8) {
            SectionLabelView(text: viewModel.state.announcementsLabel)
            ForEach(Array((viewModel.state.announcements as? [AnnouncementState] ?? []).enumerated()), id: \.offset) { _, announcement in
                AnnouncementItemView(text: announcement.text)
            }
        }
    }
    
    private var quizzesSection: some View {
        VStack(alignment: .leading, spacing: 12) {
            SectionLabelView(text: viewModel.state.recentQuizzesLabel)
            
            let quizzes = viewModel.state.recentQuizzes as? [TutorQuizState] ?? []
            if quizzes.isEmpty {
                Text("No quizzes yet. Create your first quiz.")
                    .font(GyansarTypography.bodyMedium)
                    .foregroundColor(theme.onBackground.opacity(0.7))
            } else {
                ForEach(Array(quizzes.enumerated()), id: \.offset) { _, quiz in
                    TutorQuizCardView(
                        title: quiz.title,
                        subject: quiz.subject,
                        createdLabel: quiz.createdLabel,
                        action: { onQuizSelected(quiz.quizId) }
                    )
                }
            }
        }
    }
}

@MainActor
class TutorDashboardViewModel: ObservableObject {
    @Published var state: TutorDashboardState
    
    private var sharedViewModel: GyansarTutorDashboardViewModel?
    
    init() {
        state = GyansarWireframeData.shared.gallery.tutorDashboard
        
        Task {
            await loadFromSharedViewModel()
        }
    }
    
    private func loadFromSharedViewModel() async {
        do {
            sharedViewModel = KoinDependencies.shared.tutorDashboardViewModel
        }
    }
}
