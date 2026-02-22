package com.jetbrains.kmpapp.navigation

sealed class GyansarDestination {
    data object Login : GyansarDestination()
    data object ProfileSelection : GyansarDestination()
    data object StudentDashboard : GyansarDestination()
    data object CreateTest : GyansarDestination()
    data class Assessment(val quizId: Long) : GyansarDestination()
    data class Results(val quizId: Long) : GyansarDestination()
    data object TutorDashboard : GyansarDestination()
    data object CreateQuiz : GyansarDestination()
    data class QuizReview(val quizId: Long) : GyansarDestination()
    data class AddQuestion(val quizId: Long) : GyansarDestination()
    data class QuizAnalytics(val quizId: Long) : GyansarDestination()

    fun toRoute(): String = when (this) {
        is Login -> GyansarRoutes.LOGIN
        is ProfileSelection -> GyansarRoutes.PROFILE_SELECTION
        is StudentDashboard -> GyansarRoutes.STUDENT_DASHBOARD
        is CreateTest -> GyansarRoutes.CREATE_TEST
        is Assessment -> GyansarRoutes.assessmentRoute(quizId)
        is Results -> GyansarRoutes.resultsRoute(quizId)
        is TutorDashboard -> GyansarRoutes.TUTOR_DASHBOARD
        is CreateQuiz -> GyansarRoutes.CREATE_QUIZ
        is QuizReview -> GyansarRoutes.quizReviewRoute(quizId)
        is AddQuestion -> GyansarRoutes.addQuestionRoute(quizId)
        is QuizAnalytics -> GyansarRoutes.quizAnalyticsRoute(quizId)
    }
}
