package com.jetbrains.kmpapp.feature.dashboard.presentation

import com.jetbrains.kmpapp.feature.student.domain.AiTestRequest
import com.jetbrains.kmpapp.feature.student.domain.StudentDashboardSnapshot
import com.jetbrains.kmpapp.feature.student.domain.usecase.CreateAiTestUseCase
import com.jetbrains.kmpapp.feature.student.domain.usecase.ObserveActiveStudentUseCase
import com.jetbrains.kmpapp.feature.student.domain.usecase.ObserveStudentDashboardUseCase
import com.jetbrains.kmpapp.feature.wireframe.presentation.GyansarWireframeData
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.coroutines.launch

class GyansarStudentDashboardViewModel(
    observeActiveStudent: ObserveActiveStudentUseCase,
    private val observeDashboard: ObserveStudentDashboardUseCase,
    private val createAiTest: CreateAiTestUseCase,
    private val clock: Clock = Clock.System,
    private val timeZone: TimeZone = TimeZone.currentSystemDefault()
) : ViewModel() {

    private val baseState = GyansarWireframeData.gallery.studentDashboard.copy(
        isStudentMissing = true,
        studentId = ""
    )
    private val aiTestForm = MutableStateFlow(baseState.aiTestForm)

    private val dashboardSnapshot = observeActiveStudent()
        .flatMapLatest { student ->
            val studentId = student?.id.orEmpty()
            if (studentId.isBlank()) {
                flowOf(emptySnapshot())
            } else {
                observeDashboard(studentId)
            }
        }

    @NativeCoroutinesState
    val state: StateFlow<StudentDashboardState> = combine(dashboardSnapshot, aiTestForm) { snapshot, form ->
        mapToState(snapshot, form)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), baseState)

    fun updateAiTestForm(update: AiTestFormState) {
        aiTestForm.value = update
    }

    fun toggleSmartSelection(enabled: Boolean) {
        aiTestForm.value = aiTestForm.value.copy(smartSelectionEnabled = enabled)
    }

    fun toggleInstantFeedback(enabled: Boolean) {
        aiTestForm.value = aiTestForm.value.copy(instantFeedbackEnabled = enabled)
    }

    fun updateQuestionCount(value: String) {
        aiTestForm.value = aiTestForm.value.copy(questionCount = value)
    }

    fun updateTimeLimit(value: String) {
        aiTestForm.value = aiTestForm.value.copy(timeLimitMinutes = value)
    }

    fun updateTitle(value: String) {
        aiTestForm.value = aiTestForm.value.copy(title = value)
    }

    fun updateSubject(value: String) {
        aiTestForm.value = aiTestForm.value.copy(subject = value)
    }

    fun updateTopics(value: String) {
        aiTestForm.value = aiTestForm.value.copy(topics = value)
    }

    fun createAiTest(studentId: String) {
        if (studentId.isBlank()) {
            aiTestForm.value = aiTestForm.value.copy(statusMessage = "Add a student ID first.")
            return
        }
        viewModelScope.launch {
            val form = aiTestForm.value
            val questionCount = form.questionCount.toIntOrNull() ?: 10
            val timeLimit = form.timeLimitMinutes.toIntOrNull() ?: 15
            val result = createAiTest(
                AiTestRequest(
                    studentId = studentId,
                    title = form.title.ifBlank { "AI Smart Test" },
                    subject = form.subject.ifBlank { "Practice" },
                    topics = form.topics,
                    questionCount = questionCount,
                    timeLimitMinutes = timeLimit,
                    smartSelectionEnabled = form.smartSelectionEnabled,
                    instantFeedbackEnabled = form.instantFeedbackEnabled
                )
            )
            aiTestForm.value = form.copy(
                statusMessage = result?.let { "Created AI test • Quiz #${it.quizId}" }
                    ?: "Unable to create test. Check inputs."
            )
        }
    }

    private fun mapToState(
        snapshot: StudentDashboardSnapshot,
        form: AiTestFormState
    ): StudentDashboardState {
        val studentId = snapshot.student?.id.orEmpty()
        val greeting = if (studentId.isBlank()) {
            "Add your Student ID"
        } else {
            "Hi ${snapshot.student?.displayName ?: "Scholar"},"
        }
        val subtext = if (studentId.isBlank()) {
            "Enter your ID to unlock your AI dashboard."
        } else {
            "Keep the streak alive today."
        }
        val initials = snapshot.student?.displayName
            ?.split(" ")
            ?.mapNotNull { it.firstOrNull()?.toString() }
            ?.joinToString("")
            ?.take(2)
            ?.uppercase() ?: "ST"
        val recentTests = snapshot.activities.take(6).map { activity ->
            val date = activity.occurredAt.toLocalDateTime(timeZone).date
            RecentTestState(
                quizId = null,
                subject = activity.subject,
                score = activity.score?.let { "$it%" } ?: "--",
                date = "${date.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${date.dayOfMonth}",
                title = activity.title,
                tags = buildList {
                    if (activity.smartSelectionEnabled) add("Smart")
                    if (activity.hasInstantFeedback) add("Instant feedback")
                    add(activity.type.name.lowercase().replaceFirstChar { it.uppercase() })
                }
            )
        }
        val flashcards = snapshot.flashcards.take(6).map {
            FlashcardCardState(
                id = it.id,
                prompt = it.prompt,
                topic = it.topic,
                mastery = it.mastery,
                isStarred = it.isStarred
            )
        }
        val stats = listOf(
            StatState("Active Days", snapshot.activeDays.toString()),
            StatState("Tests Taken", snapshot.totalQuizzes.toString()),
            StatState("Flashcards", snapshot.totalFlashcards.toString())
        )
        val gamification = GamificationState(
            level = "Level ${(snapshot.totalQuizzes / 3) + 1}",
            xp = "${(snapshot.activeDays * 50) + (snapshot.totalQuizzes * 20)} XP",
            badges = listOfNotNull(
                if (snapshot.streakDays >= 3) "Consistency" else null,
                if (snapshot.totalFlashcards >= 10) "Memory Maker" else null
            )
        )

        return baseState.copy(
            studentId = studentId,
            greeting = greeting,
            subtext = subtext,
            initials = initials,
            stats = stats,
            streakValue = "${snapshot.streakDays} day streak",
            recentTests = recentTests,
            flashcards = flashcards,
            aiTestForm = form,
            gamification = gamification,
            isStudentMissing = studentId.isBlank()
        )
    }

    private fun emptySnapshot(): StudentDashboardSnapshot {
        return StudentDashboardSnapshot(
            student = null,
            activities = emptyList(),
            flashcards = emptyList(),
            streakDays = 0,
            activeDays = 0,
            totalQuizzes = 0,
            totalFlashcards = 0
        )
    }
}
