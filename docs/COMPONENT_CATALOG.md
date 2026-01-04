# Component Catalog

This catalog lists the key components involved in the student dashboard flow so they are easy to find and debug.

## App entry and navigation

| Component | Role | Location |
| --- | --- | --- |
| `App` | Declares navigation graph and wires screens via Koin view models. | `composeApp/src/androidMain/kotlin/com/jetbrains/kmpapp/App.kt` |
| `GyansarRoutes` | Route constants for navigation. | `composeApp/src/androidMain/kotlin/com/jetbrains/kmpapp/App.kt` |

## Student onboarding and dashboard

| Component | Role | Location |
| --- | --- | --- |
| `StudentIdentityScreen` | Collects student ID and optional display name before loading the dashboard. | `composeApp/src/androidMain/kotlin/com/jetbrains/kmpapp/feature/dashboard/ui/StudentIdentityScreen.kt` |
| `StudentDashboardScreen` | Main student home with stats, AI test builder, activity log, flashcard rail, and gamification. | `composeApp/src/androidMain/kotlin/com/jetbrains/kmpapp/feature/dashboard/ui/StudentDashboardScreen.kt` |
| `StudentIdentityViewModel` | Saves active student via Koin and exposes input state. | `shared/src/commonMain/kotlin/com/jetbrains/kmpapp/feature/dashboard/presentation/StudentIdentityViewModel.kt` |
| `GyansarStudentDashboardViewModel` | Derives dashboard UI state from student activities/flashcards and AI test form settings. | `shared/src/commonMain/kotlin/com/jetbrains/kmpapp/feature/dashboard/presentation/GyansarStudentDashboardViewModel.kt` |
| `DashboardStates` | UI state models for dashboard (stats, flashcards, AI test form, gamification). | `shared/src/commonMain/kotlin/com/jetbrains/kmpapp/feature/dashboard/presentation/DashboardStates.kt` |

## AI test creation and logging

| Component | Role | Location |
| --- | --- | --- |
| `CreateAiTestUseCase` | Builds a quiz, seeds questions, logs an activity, and generates flashcards. | `shared/src/commonMain/kotlin/com/jetbrains/kmpapp/feature/student/domain/usecase/CreateAiTestUseCase.kt` |
| `ObserveStudentDashboardUseCase` | Streams dashboard snapshot (student, activities, flashcards, streaks). | `shared/src/commonMain/kotlin/com/jetbrains/kmpapp/feature/student/domain/usecase/ObserveStudentDashboardUseCase.kt` |
| `SetActiveStudentUseCase` / `ObserveActiveStudentUseCase` | Manage and observe the active student profile. | `shared/src/commonMain/kotlin/com/jetbrains/kmpapp/feature/student/domain/usecase` |
| `StudentModels` | Domain models for students, activities, flashcards, and AI test requests/results. | `shared/src/commonMain/kotlin/com/jetbrains/kmpapp/feature/student/domain/StudentModels.kt` |

## Data layer

| Component | Role | Location |
| --- | --- | --- |
| `StudentProgressRepository` | Abstraction for student profile/activity/flashcard persistence. | `shared/src/commonMain/kotlin/com/jetbrains/kmpapp/feature/student/domain/StudentProgressRepository.kt` |
| `DefaultStudentProgressRepository` | Default implementation delegating to a local data source. | `shared/src/commonMain/kotlin/com/jetbrains/kmpapp/feature/student/data/DefaultStudentProgressRepository.kt` |
| `RoomStudentLocalDataSource` | Android Room-backed student storage. | `shared/src/androidMain/kotlin/com/jetbrains/kmpapp/feature/student/data/local/RoomStudentLocalDataSource.kt` |
| `InMemoryStudentLocalDataSource` | iOS in-memory student storage. | `shared/src/iosMain/kotlin/com/jetbrains/kmpapp/feature/student/data/local/InMemoryStudentLocalDataSource.kt` |
| `GyansarDatabase` | Room database including quizzes, activities, flashcards, and student profiles. | `shared/src/androidMain/kotlin/com/jetbrains/kmpapp/feature/quiz/data/local/db/GyansarDatabase.kt` |
| `StudentDao` | Room DAO for student profiles, activities, and flashcards. | `shared/src/androidMain/kotlin/com/jetbrains/kmpapp/feature/student/data/local/dao/StudentDao.kt` |
| `StudentEntities` | Room entities: `StudentProfileEntity`, `StudentActivityEntity`, `FlashcardEntity`. | `shared/src/androidMain/kotlin/com/jetbrains/kmpapp/feature/student/data/local/entity` |

## Dependency injection

| Component | Role | Location |
| --- | --- | --- |
| `studentModule` | Registers repository and student use cases. | `shared/src/commonMain/kotlin/com/jetbrains/kmpapp/feature/student/di/StudentModule.kt` |
| `dashboardModule` | Wires dashboard view models. | `shared/src/commonMain/kotlin/com/jetbrains/kmpapp/feature/dashboard/di/DashboardModule.kt` |
| `platformModule` (Android) | Provides Room database, quiz and student data sources. | `shared/src/androidMain/kotlin/com/jetbrains/kmpapp/di/PlatformModules.kt` |
| `platformModule` (iOS) | Provides in-memory quiz and student data sources. | `shared/src/iosMain/kotlin/com/jetbrains/kmpapp/di/PlatformModules.kt` |
| `initKoin` | Starts Koin with all modules. | `shared/src/commonMain/kotlin/com/jetbrains/kmpapp/di/Koin.kt` |

## Shared UI components

All reusable Compose UI atoms/molecules live in `composeApp/src/androidMain/kotlin/com/jetbrains/kmpapp/ui/components/GyansarComponents.kt`. Notable components:

- `SectionLabel`, `StatCard`, `TagChip`, `StatusPill` (dashboard)
- `AvatarChip`, `BottomNav`, `FloatingActionButton`
- `PracticeCard`, `RecentTestCard`, `SummaryCard`, `MetricCard`, `Histogram`
- `AnswerOption`, `HintPill`, `TopicBar`, `ReviewAnswerCard`
- `ClassCard`, `AnnouncementItem`, `QuestionReviewCard`, `SegmentedControl`, `TabPillRow`

Use this file as the single source for small UI building blocks when debugging or adding new screens.

## Theming

| Component | Role | Location |
| --- | --- | --- |
| `GyansarTheme` | Light/dark color schemes and typography for the app. | `composeApp/src/androidMain/kotlin/com/jetbrains/kmpapp/ui/theme/GyansarTheme.kt` |

