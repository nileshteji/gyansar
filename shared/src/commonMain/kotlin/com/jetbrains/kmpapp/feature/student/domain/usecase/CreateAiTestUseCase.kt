package com.jetbrains.kmpapp.feature.student.domain.usecase

import com.jetbrains.kmpapp.feature.quiz.domain.QuestionDraft
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.AddQuestionUseCase
import com.jetbrains.kmpapp.feature.quiz.domain.usecase.CreateQuizUseCase
import com.jetbrains.kmpapp.feature.student.domain.ActivityType
import com.jetbrains.kmpapp.feature.student.domain.AiTestRequest
import com.jetbrains.kmpapp.feature.student.domain.AiTestResult
import com.jetbrains.kmpapp.feature.student.domain.Flashcard
import com.jetbrains.kmpapp.feature.student.domain.StudentActivity
import com.jetbrains.kmpapp.feature.student.domain.StudentProgressRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.random.Random

class CreateAiTestUseCase(
    private val createQuiz: CreateQuizUseCase,
    private val addQuestion: AddQuestionUseCase,
    private val studentRepository: StudentProgressRepository,
    private val clock: Clock = Clock.System
) {
    suspend operator fun invoke(request: AiTestRequest): AiTestResult? {
        if (request.studentId.isBlank()) return null
        val quizId = createQuiz(request.title.trim(), request.subject.trim(), null)

        val topics = request.topics.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        val questionDrafts = buildQuestionDrafts(request, topics)
        questionDrafts.take(request.questionCount).forEach { draft ->
            addQuestion(quizId, draft)
        }

        val now = clock.now()
        val activity = StudentActivity(
            id = Random.nextLong(Long.MAX_VALUE),
            studentId = request.studentId,
            title = request.title.ifBlank { "AI Practice" },
            subject = request.subject,
            questionCount = request.questionCount,
            score = null,
            durationMinutes = request.timeLimitMinutes,
            hasInstantFeedback = request.instantFeedbackEnabled,
            smartSelectionEnabled = request.smartSelectionEnabled,
            occurredAt = now,
            type = ActivityType.TEST
        )
        studentRepository.logActivity(activity)

        val flashcards = buildFlashcards(request.studentId, topics, now)
        if (flashcards.isNotEmpty()) {
            studentRepository.saveFlashcards(flashcards)
        }

        return AiTestResult(
            quizId = quizId,
            activity = activity,
            flashcards = flashcards
        )
    }

    private fun buildQuestionDrafts(
        request: AiTestRequest,
        topics: List<String>
    ): List<QuestionDraft> {
        if (topics.isEmpty()) {
            return List(request.questionCount.coerceAtLeast(1)) { index ->
                QuestionDraft(
                    prompt = "Question ${index + 1} about ${request.subject}",
                    options = listOf("Option A", "Option B", "Option C", "Option D"),
                    correctAnswer = "Option A",
                    difficulty = if (request.smartSelectionEnabled) "Adaptive" else "Standard",
                    bloomLevel = if (request.instantFeedbackEnabled) "Understand" else "Remember"
                )
            }
        }
        return topics.flatMap { topic ->
            listOf(
                QuestionDraft(
                    prompt = "Explain $topic in your own words.",
                    options = listOf("Summary", "Formula", "Example", "Diagram"),
                    correctAnswer = "Summary",
                    difficulty = if (request.smartSelectionEnabled) "Adaptive" else "Medium",
                    bloomLevel = "Understand"
                ),
                QuestionDraft(
                    prompt = "Apply $topic to a real-world scenario.",
                    options = listOf("Setup", "Calculate", "Analyze", "Reflect"),
                    correctAnswer = "Analyze",
                    difficulty = "Hard",
                    bloomLevel = "Apply"
                )
            )
        }
    }

    private fun buildFlashcards(
        studentId: String,
        topics: List<String>,
        now: Instant
    ): List<Flashcard> {
        return topics.mapIndexed { index, topic ->
            Flashcard(
                id = Random.nextLong(Long.MAX_VALUE),
                studentId = studentId,
                prompt = "Key idea: $topic",
                answer = "Remember the core formula or definition for $topic.",
                topic = topic,
                createdAt = now,
                mastery = 0,
                isStarred = true
            )
        }
    }
}
