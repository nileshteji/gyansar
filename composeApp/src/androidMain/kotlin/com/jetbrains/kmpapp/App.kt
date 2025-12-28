package com.jetbrains.kmpapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jetbrains.kmpapp.feature.assessment.presentation.GyansarAssessmentViewModel
import com.jetbrains.kmpapp.feature.assessment.ui.AssessmentScreen
import com.jetbrains.kmpapp.feature.assessment.ui.CreateTestConfigScreen
import com.jetbrains.kmpapp.feature.assessment.ui.PerformanceAnalyticsScreen
import com.jetbrains.kmpapp.feature.auth.ui.LoginScreen
import com.jetbrains.kmpapp.feature.auth.ui.ProfileSelectionScreen
import com.jetbrains.kmpapp.feature.dashboard.presentation.GyansarStudentDashboardViewModel
import com.jetbrains.kmpapp.feature.dashboard.presentation.GyansarTutorDashboardViewModel
import com.jetbrains.kmpapp.feature.dashboard.ui.StudentDashboardScreen
import com.jetbrains.kmpapp.feature.dashboard.ui.TutorDashboardScreen
import com.jetbrains.kmpapp.feature.quiz.presentation.GyansarQuizDetailViewModel
import com.jetbrains.kmpapp.feature.quiz.presentation.GyansarQuizEditorViewModel
import com.jetbrains.kmpapp.feature.quiz.presentation.QuizAnalyticsState
import com.jetbrains.kmpapp.feature.quiz.presentation.QuizQuestionState
import com.jetbrains.kmpapp.feature.quiz.presentation.QuizReviewState
import com.jetbrains.kmpapp.feature.quiz.ui.AddQuestionScreen
import com.jetbrains.kmpapp.feature.quiz.ui.CreateQuizScreen
import com.jetbrains.kmpapp.feature.quiz.ui.QuizAnalyticsScreen
import com.jetbrains.kmpapp.feature.quiz.ui.QuizReviewScreen
import com.jetbrains.kmpapp.feature.wireframe.presentation.GyansarWireframeData
import com.jetbrains.kmpapp.ui.theme.GyansarTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun App() {
    GyansarTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            val labels = GyansarWireframeData.gallery
            val scope = rememberCoroutineScope()

            Scaffold(contentWindowInsets = WindowInsets.safeDrawing) { padding ->
                NavHost(
                    navController = navController,
                    startDestination = GyansarRoutes.Login,
                    modifier = Modifier.padding(padding)
                ) {
                    composable(GyansarRoutes.Login) {
                        LoginScreen(
                            state = labels.login,
                            onSignIn = { navController.navigate(GyansarRoutes.ProfileSelection) }
                        )
                    }
                    composable(GyansarRoutes.ProfileSelection) {
                        ProfileSelectionScreen(
                            state = labels.profileSelection,
                            onStudentClick = { navController.navigate(GyansarRoutes.StudentDashboard) },
                            onTutorClick = { navController.navigate(GyansarRoutes.TutorDashboard) }
                        )
                    }
                    composable(GyansarRoutes.StudentDashboard) {
                        val viewModel: GyansarStudentDashboardViewModel = koinViewModel()
                        val state by viewModel.state.collectAsStateWithLifecycle()
                        StudentDashboardScreen(
                            state = state,
                            onCreateTest = { navController.navigate(GyansarRoutes.CreateTest) },
                            onRecentTestClick = { quizId ->
                                navController.navigate(GyansarRoutes.assessmentRoute(quizId))
                            }
                        )
                    }
                    composable(GyansarRoutes.CreateTest) {
                        val editorViewModel: GyansarQuizEditorViewModel = koinViewModel()
                        CreateTestConfigScreen(
                            state = labels.createTest,
                            onGenerateTest = {
                                scope.launch {
                                    val quizId = editorViewModel.createSamplePracticeQuiz()
                                    if (quizId != null) {
                                        navController.navigate(GyansarRoutes.assessmentRoute(quizId))
                                    }
                                }
                            }
                        )
                    }
                    composable(
                        route = GyansarRoutes.Assessment,
                        arguments = listOf(navArgument(GyansarRoutes.Args.QuizId) { type = NavType.LongType })
                    ) { backStackEntry ->
                        val quizId = backStackEntry.arguments?.getLong(GyansarRoutes.Args.QuizId) ?: return@composable
                        val viewModel: GyansarAssessmentViewModel = koinViewModel()
                        LaunchedEffect(quizId) {
                            viewModel.setQuizId(quizId)
                        }
                        val state by viewModel.state.collectAsStateWithLifecycle()
                        AssessmentScreen(
                            state = state,
                            onNext = { navController.navigate(GyansarRoutes.resultsRoute(quizId)) },
                            onPrevious = { viewModel.previous() },
                            onSelectAnswer = { index -> viewModel.selectAnswer(index) }
                        )
                    }
                    composable(
                        route = GyansarRoutes.Results,
                        arguments = listOf(navArgument(GyansarRoutes.Args.QuizId) { type = NavType.LongType })
                    ) {
                        PerformanceAnalyticsScreen(
                            state = labels.performanceAnalytics,
                            onBack = { navController.popBackStack(GyansarRoutes.StudentDashboard, false) }
                        )
                    }
                    composable(GyansarRoutes.TutorDashboard) {
                        val viewModel: GyansarTutorDashboardViewModel = koinViewModel()
                        val state by viewModel.state.collectAsStateWithLifecycle()
                        TutorDashboardScreen(
                            state = state,
                            onCreateQuiz = { navController.navigate(GyansarRoutes.CreateQuiz) },
                            onQuizSelected = { quizId ->
                                navController.navigate(GyansarRoutes.quizReviewRoute(quizId))
                            }
                        )
                    }
                    composable(GyansarRoutes.CreateQuiz) {
                        val editorViewModel: GyansarQuizEditorViewModel = koinViewModel()
                        CreateQuizScreen(
                            onBack = { navController.popBackStack() },
                            onCreate = { title, subject ->
                                scope.launch {
                                    val quizId = editorViewModel.createQuiz(title, subject, null)
                                    navController.navigate(GyansarRoutes.quizReviewRoute(quizId))
                                }
                            }
                        )
                    }
                    composable(
                        route = GyansarRoutes.QuizReview,
                        arguments = listOf(navArgument(GyansarRoutes.Args.QuizId) { type = NavType.LongType })
                    ) { backStackEntry ->
                        val quizId = backStackEntry.arguments?.getLong(GyansarRoutes.Args.QuizId) ?: return@composable
                        val viewModel: GyansarQuizDetailViewModel = koinViewModel()
                        LaunchedEffect(quizId) {
                            viewModel.setQuizId(quizId)
                        }
                        val quiz by viewModel.quiz.collectAsStateWithLifecycle()
                        val reviewState = quiz?.let { data ->
                            QuizReviewState(
                                title = "Review Quiz: ${data.quiz.title}",
                                questions = data.questions.map { question ->
                                    QuizQuestionState(
                                        question = question.prompt,
                                        answers = question.options.joinToString(", "),
                                        tags = listOf(question.difficulty, question.bloomLevel)
                                    )
                                },
                                editLabel = "Edit",
                                deleteLabel = "Delete",
                                addButtonText = "Add Question",
                                finalizeButtonText = "Finalize"
                            )
                        } ?: QuizReviewState(
                            title = "Review Quiz",
                            questions = emptyList(),
                            editLabel = "Edit",
                            deleteLabel = "Delete",
                            addButtonText = "Add Question",
                            finalizeButtonText = "Finalize"
                        )
                        QuizReviewScreen(
                            state = reviewState,
                            onAddQuestion = { navController.navigate(GyansarRoutes.addQuestionRoute(quizId)) },
                            onFinalize = { navController.navigate(GyansarRoutes.quizAnalyticsRoute(quizId)) },
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable(
                        route = GyansarRoutes.AddQuestion,
                        arguments = listOf(navArgument(GyansarRoutes.Args.QuizId) { type = NavType.LongType })
                    ) { backStackEntry ->
                        val quizId = backStackEntry.arguments?.getLong(GyansarRoutes.Args.QuizId) ?: return@composable
                        val editorViewModel: GyansarQuizEditorViewModel = koinViewModel()
                        AddQuestionScreen(
                            onBack = { navController.popBackStack() },
                            onSave = { question ->
                                scope.launch {
                                    editorViewModel.addQuestion(quizId, question)
                                    navController.popBackStack()
                                }
                            }
                        )
                    }
                    composable(
                        route = GyansarRoutes.QuizAnalytics,
                        arguments = listOf(navArgument(GyansarRoutes.Args.QuizId) { type = NavType.LongType })
                    ) {
                        val analyticsState = QuizAnalyticsState(
                            title = "Quiz Analytics",
                            tabs = emptyList(),
                            selectedTabIndex = 0,
                            metrics = emptyList(),
                            scoreDistributionLabel = "",
                            histogramBars = emptyList(),
                            histogramLabels = emptyList(),
                            toughestQuestionLabel = "",
                            toughestQuestion = ""
                        )
                        QuizAnalyticsScreen(
                            state = analyticsState,
                            onBack = { navController.popBackStack(GyansarRoutes.TutorDashboard, false) }
                        )
                    }
                }
            }
        }
    }
}

private object GyansarRoutes {
    const val Login = "login"
    const val ProfileSelection = "profile"
    const val StudentDashboard = "student/dashboard"
    const val CreateTest = "student/create-test"
    const val Assessment = "student/assessment/{quizId}"
    const val Results = "student/results/{quizId}"
    const val TutorDashboard = "tutor/dashboard"
    const val CreateQuiz = "tutor/create-quiz"
    const val QuizReview = "tutor/quiz/{quizId}"
    const val AddQuestion = "tutor/quiz/{quizId}/add-question"
    const val QuizAnalytics = "tutor/quiz/{quizId}/analytics"

    object Args {
        const val QuizId = "quizId"
    }

    fun assessmentRoute(quizId: Long) = "student/assessment/$quizId"
    fun resultsRoute(quizId: Long) = "student/results/$quizId"
    fun quizReviewRoute(quizId: Long) = "tutor/quiz/$quizId"
    fun addQuestionRoute(quizId: Long) = "tutor/quiz/$quizId/add-question"
    fun quizAnalyticsRoute(quizId: Long) = "tutor/quiz/$quizId/analytics"
}
