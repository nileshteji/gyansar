import SwiftUI
import Shared

enum GyansarRoute: Hashable {
    case login
    case profileSelection
    case studentDashboard
    case createTest
    case assessment(quizId: Int64)
    case results(quizId: Int64)
    case tutorDashboard
    case createQuiz
    case quizReview(quizId: Int64)
    case addQuestion(quizId: Int64)
    case quizAnalytics(quizId: Int64)
}

@MainActor
class GyansarNavigatorState: ObservableObject {
    @Published var path = NavigationPath()
    
    func navigate(to route: GyansarRoute) {
        path.append(route)
    }
    
    func popBack() {
        if !path.isEmpty {
            path.removeLast()
        }
    }
    
    func popToRoot() {
        path = NavigationPath()
    }
    
    func popTo(_ route: GyansarRoute) {
        while !path.isEmpty {
            path.removeLast()
        }
        path.append(route)
    }
}

struct GyansarNavigator: View {
    @StateObject private var navigator = GyansarNavigatorState()
    @StateObject private var theme = GyansarTheme()
    @Environment(\.colorScheme) private var systemColorScheme
    
    var body: some View {
        NavigationStack(path: $navigator.path) {
            LoginView(
                onSignIn: {
                    navigator.navigate(to: .profileSelection)
                }
            )
            .navigationDestination(for: GyansarRoute.self) { route in
                destinationView(for: route)
            }
        }
        .environmentObject(navigator)
        .environmentObject(theme)
        .onAppear {
            theme.colorScheme = systemColorScheme
        }
        .onChange(of: systemColorScheme) { newScheme in
            theme.colorScheme = newScheme
        }
    }
    
    @ViewBuilder
    private func destinationView(for route: GyansarRoute) -> some View {
        switch route {
        case .login:
            LoginView(onSignIn: { navigator.navigate(to: .profileSelection) })
            
        case .profileSelection:
            ProfileSelectionView(
                onStudentClick: { navigator.navigate(to: .studentDashboard) },
                onTutorClick: { navigator.navigate(to: .tutorDashboard) }
            )
            
        case .studentDashboard:
            StudentDashboardView(
                onCreateTest: { navigator.navigate(to: .createTest) },
                onRecentTestClick: { quizId in
                    navigator.navigate(to: .assessment(quizId: quizId))
                }
            )
            
        case .createTest:
            CreateTestConfigView(
                onGenerateTest: { quizId in
                    navigator.navigate(to: .assessment(quizId: quizId))
                }
            )
            
        case .assessment(let quizId):
            AssessmentView(
                quizId: quizId,
                onNext: { navigator.navigate(to: .results(quizId: quizId)) },
                onPrevious: { navigator.popBack() }
            )
            
        case .results(let quizId):
            PerformanceAnalyticsView(
                quizId: quizId,
                onBack: {
                    navigator.popTo(.studentDashboard)
                }
            )
            
        case .tutorDashboard:
            TutorDashboardView(
                onCreateQuiz: { navigator.navigate(to: .createQuiz) },
                onQuizSelected: { quizId in
                    navigator.navigate(to: .quizReview(quizId: quizId))
                }
            )
            
        case .createQuiz:
            CreateQuizView(
                onBack: { navigator.popBack() },
                onCreate: { quizId in
                    navigator.navigate(to: .quizReview(quizId: quizId))
                }
            )
            
        case .quizReview(let quizId):
            QuizReviewView(
                quizId: quizId,
                onAddQuestion: { navigator.navigate(to: .addQuestion(quizId: quizId)) },
                onFinalize: { navigator.navigate(to: .quizAnalytics(quizId: quizId)) },
                onBack: { navigator.popBack() }
            )
            
        case .addQuestion(let quizId):
            AddQuestionView(
                quizId: quizId,
                onBack: { navigator.popBack() },
                onSave: { navigator.popBack() }
            )
            
        case .quizAnalytics(let quizId):
            QuizAnalyticsView(
                quizId: quizId,
                onBack: {
                    navigator.popTo(.tutorDashboard)
                }
            )
        }
    }
}
