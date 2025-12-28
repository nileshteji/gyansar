package com.jetbrains.kmpapp.feature.wireframe.presentation

import com.jetbrains.kmpapp.feature.assessment.presentation.AnswerOptionState
import com.jetbrains.kmpapp.feature.assessment.presentation.AssessmentState
import com.jetbrains.kmpapp.feature.assessment.presentation.CreateTestConfigState
import com.jetbrains.kmpapp.feature.assessment.presentation.PerformanceAnalyticsState
import com.jetbrains.kmpapp.feature.assessment.presentation.ReviewAnswerState
import com.jetbrains.kmpapp.feature.assessment.presentation.TopicPerformanceState
import com.jetbrains.kmpapp.feature.auth.presentation.LoginScreenState
import com.jetbrains.kmpapp.feature.auth.presentation.ProfileOptionState
import com.jetbrains.kmpapp.feature.auth.presentation.ProfileSelectionState
import com.jetbrains.kmpapp.feature.dashboard.presentation.AnnouncementState
import com.jetbrains.kmpapp.feature.dashboard.presentation.ClassSummaryState
import com.jetbrains.kmpapp.feature.dashboard.presentation.PracticeState
import com.jetbrains.kmpapp.feature.dashboard.presentation.RecentTestState
import com.jetbrains.kmpapp.feature.dashboard.presentation.StatState
import com.jetbrains.kmpapp.feature.dashboard.presentation.StudentDashboardState
import com.jetbrains.kmpapp.feature.dashboard.presentation.TutorDashboardState
import com.jetbrains.kmpapp.feature.quiz.presentation.MetricState
import com.jetbrains.kmpapp.feature.quiz.presentation.QuizAnalyticsState
import com.jetbrains.kmpapp.feature.quiz.presentation.QuizQuestionState
import com.jetbrains.kmpapp.feature.quiz.presentation.QuizReviewState

data class GyansarWireframeGalleryState(
    val titles: GyansarWireframeTitles,
    val login: LoginScreenState,
    val profileSelection: ProfileSelectionState,
    val studentDashboard: StudentDashboardState,
    val createTest: CreateTestConfigState,
    val assessment: AssessmentState,
    val performanceAnalytics: PerformanceAnalyticsState,
    val tutorDashboard: TutorDashboardState,
    val quizReview: QuizReviewState,
    val quizAnalytics: QuizAnalyticsState
)

data class GyansarWireframeTitles(
    val login: String,
    val profileSelection: String,
    val studentDashboard: String,
    val createTest: String,
    val assessment: String,
    val performanceAnalytics: String,
    val tutorDashboard: String,
    val quizReview: String,
    val quizAnalytics: String
)

object GyansarWireframeData {
    val gallery = GyansarWireframeGalleryState(
        titles = GyansarWireframeTitles(
            login = "1.1 Login",
            profileSelection = "1.2 Profile Selection",
            studentDashboard = "2.1 Student Dashboard",
            createTest = "2.2 Create Test",
            assessment = "2.3 Assessment Interface",
            performanceAnalytics = "2.4 Performance Analytics",
            tutorDashboard = "3.1 Tutor Dashboard",
            quizReview = "3.2 Quiz Review and Edit",
            quizAnalytics = "3.3 Quiz Analytics"
        ),
        login = LoginScreenState(
            tagline = "Your AI-powered learning companion.",
            buttonText = "Sign In with Google",
            termsText = "By signing in, you agree to our Privacy Policy and Terms of Service."
        ),
        profileSelection = ProfileSelectionState(
            header = "Tell us who you are",
            options = listOf(
                ProfileOptionState(
                    title = "I am a Student",
                    description = "Create tests, track your mastery, and practice for exams.",
                    badge = "Student"
                ),
                ProfileOptionState(
                    title = "I am a Tutor",
                    description = "Create quizzes, manage classes, and view student analytics.",
                    badge = "Tutor"
                )
            )
        ),
        studentDashboard = StudentDashboardState(
            greeting = "Welcome back, Aarav!",
            subtext = "Ready to keep your streak alive?",
            initials = "AA",
            stats = listOf(
                StatState(title = "Day Streak", value = "21"),
                StatState(title = "Total XP", value = "15,400")
            ),
            practiceLabel = "Today's Practice",
            practice = PracticeState(
                title = "You have 15 flashcards to review.",
                subtitle = "Daily review is ready",
                cta = ">"
            ),
            recentTestsLabel = "Recent Tests",
            recentTests = listOf(
                RecentTestState(subject = "Physics", score = "85%", date = "Aug 14"),
                RecentTestState(subject = "Chemistry", score = "72%", date = "Aug 12"),
                RecentTestState(subject = "Maths", score = "91%", date = "Aug 10")
            ),
            navItems = listOf("Home", "Library", "Practice", "Profile"),
            selectedNavIndex = 0
        ),
        createTest = CreateTestConfigState(
            title = "Create Test",
            documentName = "Electromagnetic Induction.pdf",
            questionTypeLabel = "Question Type",
            questionTypes = listOf("MCQ", "Long Answer"),
            selectedQuestionType = 0,
            difficultyLabel = "Difficulty",
            difficultyLevels = listOf("Easy", "Medium", "Hard"),
            selectedDifficulty = 1,
            questionCountLabel = "Number of Questions",
            questionRange = 1..50,
            defaultQuestionCount = 20,
            timeLimitLabel = "Time Limit (minutes)",
            timeLimitMinutes = "30",
            generateButtonText = "Generate Test"
        ),
        assessment = AssessmentState(
            progressLabel = "Question 5 / 20",
            timerLabel = "25:10",
            flagLabel = "Flag",
            questionText = "What principle states that the induced electromotive force will produce a current that opposes the change in magnetic flux?",
            answers = listOf(
                AnswerOptionState(text = "Faraday's Law", selected = false),
                AnswerOptionState(text = "Lenz's Law", selected = true),
                AnswerOptionState(text = "Gauss's Law", selected = false),
                AnswerOptionState(text = "Ampere's Law", selected = false)
            ),
            previousLabel = "< Previous",
            hintLabel = "Hint (-1/4)",
            nextLabel = "Next >"
        ),
        performanceAnalytics = PerformanceAnalyticsState(
            title = "Test Results",
            score = "75%",
            accuracyLabel = "Accuracy",
            accuracy = "15/20",
            timeLabel = "Time",
            time = "27:45",
            performanceLabel = "Performance by Topic",
            topicPerformance = listOf(
                TopicPerformanceState(label = "Faraday's Law", percent = 90),
                TopicPerformanceState(label = "Motional EMF", percent = 50),
                TopicPerformanceState(label = "Lenz's Law", percent = 75)
            ),
            reviewLabel = "Review Your Answers",
            reviewAnswer = ReviewAnswerState(
                question = "Q5: What principle states...",
                wrongLabel = "Your Answer",
                wrongAnswer = "Faraday's Law",
                correctLabel = "Correct Answer",
                correctAnswer = "Lenz's Law",
                cta = "Ask Gyansar Guide"
            )
        ),
        tutorDashboard = TutorDashboardState(
            title = "Dashboard",
            classesLabel = "My Classes",
            classes = listOf(
                ClassSummaryState(
                    title = "Grade 12 Physics - 2025",
                    students = "15 Students",
                    activityLabel = "Active",
                    activity = "Weekly Test #5 (12/15 done)"
                ),
                ClassSummaryState(
                    title = "Grade 11 Physics - 2026",
                    students = "20 Students",
                    activityLabel = "Active",
                    activity = "Unit 3 Quiz (18/20 done)"
                )
            ),
            announcementsLabel = "Recent Announcements",
            announcements = listOf(
                AnnouncementState(text = "Office hours moved to Friday 4 PM."),
                AnnouncementState(text = "Upload your lab notes before Wednesday.")
            ),
            recentQuizzesLabel = "My Quizzes",
            recentQuizzes = emptyList()
        ),
        quizReview = QuizReviewState(
            title = "Review Quiz: Electrostatics",
            questions = listOf(
                QuizQuestionState(
                    question = "Q1: What is the unit of electric potential?",
                    answers = "A: Volt, B: Ampere, C: Coulomb",
                    tags = listOf("Medium", "Recall")
                ),
                QuizQuestionState(
                    question = "Q2: Calculate the force between two charges...",
                    answers = "A: 2.3x10^-8 N, B: 3.1x10^-8 N",
                    tags = listOf("Hard", "Applying")
                )
            ),
            editLabel = "Edit",
            deleteLabel = "Delete",
            addButtonText = "Add Manual Question",
            finalizeButtonText = "Finalize and Assign"
        ),
        quizAnalytics = QuizAnalyticsState(
            title = "Analytics: Weekly Test #5",
            tabs = listOf("Overview", "Question Analysis", "Students"),
            selectedTabIndex = 0,
            metrics = listOf(
                MetricState(title = "Class Average", value = "68%"),
                MetricState(title = "Completion", value = "15 / 15")
            ),
            scoreDistributionLabel = "Score Distribution",
            histogramBars = listOf(0.3f, 0.6f, 0.45f, 0.8f, 0.5f, 0.7f),
            histogramLabels = listOf("40%", "60%", "80%", "100%"),
            toughestQuestionLabel = "Toughest Question",
            toughestQuestion = "Q17: Gauss's Law - 30% Correct"
        )
    )
}
