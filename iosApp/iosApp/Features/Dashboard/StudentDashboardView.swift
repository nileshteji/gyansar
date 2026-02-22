import SwiftUI
import Shared

struct StudentDashboardView: View {
    let onCreateTest: () -> Void
    let onRecentTestClick: (Int64) -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    @StateObject private var viewModel = StudentDashboardViewModel()
    
    var body: some View {
        ZStack(alignment: .bottomTrailing) {
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    headerSection
                    statsSection
                    practiceSection
                    recentTestsSection
                }
                .padding(.bottom, 80)
            }
            .background(theme.background)
            
            FABView(action: onCreateTest)
                .padding(.trailing, 16)
                .padding(.bottom, 72)
            
            VStack {
                Spacer()
                BottomNavView(
                    items: viewModel.state.navItems as? [String] ?? ["Home", "Library", "Practice", "Profile"],
                    selectedIndex: Int(viewModel.state.selectedNavIndex)
                )
            }
        }
        .navigationBarHidden(true)
    }
    
    private var headerSection: some View {
        HStack {
            VStack(alignment: .leading, spacing: 4) {
                Text(viewModel.state.greeting)
                    .font(GyansarTypography.titleLarge)
                    .foregroundColor(theme.onBackground)
                Text(viewModel.state.subtext)
                    .font(GyansarTypography.bodyMedium)
                    .foregroundColor(theme.onBackground.opacity(0.7))
            }
            Spacer()
            AvatarChipView(initials: viewModel.state.initials)
        }
    }
    
    private var statsSection: some View {
        HStack(spacing: 12) {
            ForEach(Array((viewModel.state.stats as? [StatState] ?? []).enumerated()), id: \.offset) { _, stat in
                StatCardView(title: stat.title, value: stat.value)
            }
        }
    }
    
    private var practiceSection: some View {
        VStack(alignment: .leading, spacing: 8) {
            SectionLabelView(text: viewModel.state.practiceLabel)
            PracticeCardView(
                title: viewModel.state.practice.title,
                subtitle: viewModel.state.practice.subtitle,
                cta: viewModel.state.practice.cta
            )
        }
    }
    
    private var recentTestsSection: some View {
        VStack(alignment: .leading, spacing: 8) {
            SectionLabelView(text: viewModel.state.recentTestsLabel)
            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 12) {
                    ForEach(Array((viewModel.state.recentTests as? [RecentTestState] ?? []).enumerated()), id: \.offset) { _, test in
                        RecentTestCardView(
                            subject: test.subject,
                            score: test.score,
                            date: test.date,
                            action: {
                                if let quizId = test.quizId?.int64Value {
                                    onRecentTestClick(quizId)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@MainActor
class StudentDashboardViewModel: ObservableObject {
    @Published var state: StudentDashboardState
    
    private var sharedViewModel: GyansarStudentDashboardViewModel?
    
    init() {
        state = GyansarWireframeData.shared.gallery.studentDashboard
        
        Task {
            await loadFromSharedViewModel()
        }
    }
    
    private func loadFromSharedViewModel() async {
        do {
            sharedViewModel = KoinDependencies.shared.studentDashboardViewModel
        }
    }
}
