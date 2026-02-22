package com.jetbrains.kmpapp.navigation

object GyansarRoutes {
    const val LOGIN = "login"
    const val PROFILE_SELECTION = "profile"
    const val STUDENT_DASHBOARD = "student/dashboard"
    const val CREATE_TEST = "student/create-test"
    const val ASSESSMENT = "student/assessment/{quizId}"
    const val RESULTS = "student/results/{quizId}"
    const val TUTOR_DASHBOARD = "tutor/dashboard"
    const val CREATE_QUIZ = "tutor/create-quiz"
    const val QUIZ_REVIEW = "tutor/quiz/{quizId}"
    const val ADD_QUESTION = "tutor/quiz/{quizId}/add-question"
    const val QUIZ_ANALYTICS = "tutor/quiz/{quizId}/analytics"

    object Args {
        const val QUIZ_ID = "quizId"
    }

    fun assessmentRoute(quizId: Long) = "student/assessment/$quizId"
    fun resultsRoute(quizId: Long) = "student/results/$quizId"
    fun quizReviewRoute(quizId: Long) = "tutor/quiz/$quizId"
    fun addQuestionRoute(quizId: Long) = "tutor/quiz/$quizId/add-question"
    fun quizAnalyticsRoute(quizId: Long) = "tutor/quiz/$quizId/analytics"
}
