# iOS App Migration Plan

## Overview

Replicate the Android Compose app UI in the iOS SwiftUI app, leveraging the existing shared KMP ViewModels and state classes.

---

## Current State

### Android App (composeApp/)
- **16 Kotlin files** with full feature implementation
- **Navigation**: Jetpack Compose Navigation with 10 routes (defined locally in `App.kt`)
- **Features**: Login, Profile Selection, Student Dashboard, Tutor Dashboard, Assessment, Quiz CRUD, Analytics
- **UI Components**: 25+ reusable components in `GyansarComponents.kt`

### iOS App (iosApp/)
- **4 Swift files** (basic museum list/detail views - not Gyansar-specific)
- Uses `KMPObservableViewModel` for shared ViewModel binding
- Koin initialized properly

### Shared Module
- **ViewModels**: Already shared (Dashboard, Quiz, Assessment, etc.)
- **State Classes**: All UI states defined in `commonMain`
- **DI**: Koin modules ready for iOS
- **Routes**: ❌ Currently NOT shared (duplicated in Android `App.kt`)

---

## Migration Tasks

### Phase 0: Move Routes to Shared Module (Pre-requisite) ✅ COMPLETE

> **Rationale**: Routes and navigation constants should be defined once in `shared/` and consumed by both Android and iOS, avoiding duplication.

#### 0.1 Create Shared Navigation Module
- [x] Create `GyansarRoutes.kt` in shared with route constants
- [x] Create `GyansarDestination` sealed class for type-safe navigation
- [x] Add route builder functions for parameterized routes

**Files to create:**
```
shared/src/commonMain/kotlin/com/jetbrains/kmpapp/navigation/
├── GyansarRoutes.kt          # Route string constants
└── GyansarDestination.kt     # Sealed class for type-safe destinations
```

**Example implementation:**
```kotlin
// GyansarRoutes.kt
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

// GyansarDestination.kt
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
}
```

#### 0.2 Update Android App to Use Shared Routes
- [x] Remove `GyansarRoutes` from `App.kt`
- [x] Import from `com.jetbrains.kmpapp.navigation.GyansarRoutes`
- [x] Update all route references

#### 0.3 Export for iOS
- [x] Ensure routes are accessible from Swift via KMP framework
- [x] No special iOS-side wrapper needed (Kotlin object accessible directly)

---

### Phase 1: Foundation (Day 1) ✅ COMPLETE

#### 1.1 Theme & Colors
- [x] Create `GyansarTheme.swift` with color palette matching Android theme
- [x] Define typography styles (title, body, label variants)
- [x] Add gradient helpers for hero backgrounds

**Files created:**
- `iosApp/iosApp/Theme/GyansarTheme.swift`
- `iosApp/iosApp/Theme/GyansarColors.swift`
- `iosApp/iosApp/Theme/GyansarTypography.swift`

#### 1.2 Navigation Setup
- [x] Create `GyansarNavigator.swift` with NavigationStack
- [x] Use shared `GyansarDestination` for navigation state
- [x] Map `GyansarDestination` to SwiftUI views

**Files created:**
- `iosApp/iosApp/Navigation/GyansarNavigator.swift`

---

### Phase 2: Reusable Components (Day 1-2) ✅ COMPLETE

Created SwiftUI equivalents of `GyansarComponents.kt`:

| Android Component | iOS Component | Status |
|-------------------|---------------|--------|
| `SectionLabel` | `SectionLabelView` | ✅ |
| `PrimaryButton` | `PrimaryButtonView` | ✅ |
| `SecondaryButton` | `SecondaryButtonView` | ✅ |
| `AvatarChip` | `AvatarChipView` | ✅ |
| `StatCard` | `StatCardView` | ✅ |
| `PracticeCard` | `PracticeCardView` | ✅ |
| `RecentTestCard` | `RecentTestCardView` | ✅ |
| `FloatingActionButton` | `FABView` | ✅ |
| `BottomNav` | `BottomNavView` | ✅ |
| `AnswerOption` | `AnswerOptionView` | ✅ |
| `HintPill` | `HintPillView` | ✅ |
| `ClassCard` | `ClassCardView` | ✅ |
| `AnnouncementItem` | `AnnouncementItemView` | ✅ |
| `QuestionReviewCard` | `QuestionReviewCardView` | ✅ |
| `TagChip` | `TagChipView` | ✅ |
| `TabPillRow` | `TabPillRowView` | ✅ |
| `MetricCard` | `MetricCardView` | ✅ |
| `Histogram` | `HistogramView` | ✅ |
| `HighlightCard` | `HighlightCardView` | ✅ |

**Files created:**
- `iosApp/iosApp/Components/Buttons.swift`
- `iosApp/iosApp/Components/Cards.swift`
- `iosApp/iosApp/Components/Navigation.swift`
- `iosApp/iosApp/Components/Chips.swift`
- `iosApp/iosApp/Components/Charts.swift`

---

### Phase 3: Feature Screens (Day 2-4) ✅ COMPLETE

#### 3.1 Auth Feature
- [x] `LoginView.swift` - Google Sign-In button, logo, gradient background
- [x] `ProfileSelectionView.swift` - Student/Tutor role selection

**State dependency:** `LoginScreenState`, `ProfileSelectionState` from shared

#### 3.2 Dashboard Feature
- [x] `StudentDashboardView.swift` - Stats, practice card, recent tests, FAB, bottom nav
- [x] `TutorDashboardView.swift` - Classes, announcements, quizzes, FAB

**ViewModel dependency:** 
- `GyansarStudentDashboardViewModel`
- `GyansarTutorDashboardViewModel`

#### 3.3 Assessment Feature
- [x] `AssessmentView.swift` - Question display, answer options, navigation
- [x] `CreateTestConfigView.swift` - Test configuration form
- [x] `PerformanceAnalyticsView.swift` - Results and analytics display

**ViewModel dependency:** `GyansarAssessmentViewModel`

#### 3.4 Quiz Feature (Tutor)
- [x] `CreateQuizView.swift` - Quiz creation form
- [x] `QuizReviewView.swift` - Question list with edit/delete
- [x] `AddQuestionView.swift` - Question form with options
- [x] `QuizAnalyticsView.swift` - Quiz performance metrics

**ViewModel dependency:** 
- `GyansarQuizEditorViewModel`
- `GyansarQuizDetailViewModel`

---

### Phase 4: Integration (Day 4-5) ✅ COMPLETE

#### 4.1 ViewModel Binding
- [x] Use `@StateObject` wrapper for each local ViewModel
- [x] ViewModels access shared KMP ViewModels via `KoinDependencies.shared`
- [x] Initial wireframe state from `GyansarWireframeData.shared`

**Pattern implemented:**
```swift
@StateObject private var viewModel = StudentDashboardViewModel()

class StudentDashboardViewModel: ObservableObject {
    @Published var state: StudentDashboardState
    private var sharedViewModel: GyansarStudentDashboardViewModel?
    
    init() {
        state = GyansarWireframeData.shared.gallery.studentDashboard
        sharedViewModel = KoinDependencies.shared.studentDashboardViewModel
    }
}
```

#### 4.2 Navigation Integration
- [x] Wire up all screen transitions
- [x] Handle deep linking for quiz routes
- [x] Implement back navigation

#### 4.3 Update App Entry Point
- [x] Replace `ListView()` with `GyansarNavigator()` in `iOSApp.swift`

#### 4.4 KoinDependencies Update
- [x] Added all ViewModels to `KoinDependencies.kt`
- [x] Created `koinDependencies` top-level property for Swift access

---

## File Structure (Final)

### Shared Module (Kotlin)
```
shared/src/commonMain/kotlin/com/jetbrains/kmpapp/
├── navigation/                      # NEW - Shared routes
│   ├── GyansarRoutes.kt
│   └── GyansarDestination.kt
├── feature/
│   ├── auth/presentation/
│   ├── dashboard/presentation/
│   ├── assessment/presentation/
│   └── quiz/presentation/
└── ...
```

### iOS App (Swift)
```
iosApp/iosApp/
├── iOSApp.swift                    # Entry point (update)
├── KMPObservableViewModel.swift    # Keep existing
├── Theme/
│   ├── GyansarTheme.swift
│   ├── GyansarColors.swift
│   └── GyansarTypography.swift
├── Navigation/
│   └── GyansarNavigator.swift      # Uses shared GyansarDestination
├── Components/
│   ├── Buttons.swift
│   ├── Cards.swift
│   ├── Navigation.swift
│   ├── Chips.swift
│   └── Charts.swift
├── Features/
│   ├── Auth/
│   │   ├── LoginView.swift
│   │   └── ProfileSelectionView.swift
│   ├── Dashboard/
│   │   ├── StudentDashboardView.swift
│   │   └── TutorDashboardView.swift
│   ├── Assessment/
│   │   ├── AssessmentView.swift
│   │   ├── CreateTestConfigView.swift
│   │   └── PerformanceAnalyticsView.swift
│   └── Quiz/
│       ├── CreateQuizView.swift
│       ├── QuizReviewView.swift
│       ├── AddQuestionView.swift
│       └── QuizAnalyticsView.swift
└── Utils/
    └── ViewModelExtensions.swift
```

### Android App (Updated)
```
composeApp/src/androidMain/kotlin/com/jetbrains/kmpapp/
├── App.kt                          # Updated to use shared GyansarRoutes
├── ...                             # No local GyansarRoutes object
```

---

## Shared Module Dependencies

The iOS app will use these existing shared classes:

### ViewModels (commonMain)
- `GyansarStudentDashboardViewModel`
- `GyansarTutorDashboardViewModel`
- `GyansarAssessmentViewModel`
- `GyansarQuizEditorViewModel`
- `GyansarQuizDetailViewModel`
- `GyansarQuizListViewModel`

### State Classes (commonMain)
- `StudentDashboardState`, `TutorDashboardState`
- `AssessmentState`
- `QuizReviewState`, `QuizAnalyticsState`, `QuizQuestionState`
- `LoginScreenState`, `ProfileSelectionState`

### DI (iosMain)
- `KoinKt.doInitKoin()` - Already called in `iOSApp.swift`
- `KoinDependencies` - For resolving ViewModels

---

## Estimated Effort

| Phase | Tasks | Estimated Time |
|-------|-------|----------------|
| Phase 0: Shared Routes | Move routes to shared module, update Android | 2-3 hours |
| Phase 1: Foundation | Theme, Navigation | 4-6 hours |
| Phase 2: Components | 19 reusable components | 6-8 hours |
| Phase 3: Screens | 10 feature screens | 12-16 hours |
| Phase 4: Integration | ViewModel binding, wiring | 4-6 hours |
| **Total** | | **28-39 hours** |

---

## Notes

1. **Existing files to delete/replace**: `ListView.swift`, `DetailView.swift` (museum-related)
2. **iOS-specific considerations**:
   - Use `@Published` + `Combine` for ViewModel state observation
   - Handle keyboard avoidance for form screens
   - Add safe area handling for bottom navigation
3. **Testing**: Use existing shared test infrastructure where possible

---

## Implementation Summary

### Files Created

**Shared Module (Kotlin):**
- `shared/src/commonMain/kotlin/com/jetbrains/kmpapp/navigation/GyansarRoutes.kt`
- `shared/src/commonMain/kotlin/com/jetbrains/kmpapp/navigation/GyansarDestination.kt`

**iOS App (Swift) - 20 new files:**
- Theme: `GyansarColors.swift`, `GyansarTypography.swift`, `GyansarTheme.swift`
- Navigation: `GyansarNavigator.swift`
- Components: `Buttons.swift`, `Cards.swift`, `Navigation.swift`, `Chips.swift`, `Charts.swift`
- Features/Auth: `LoginView.swift`, `ProfileSelectionView.swift`
- Features/Dashboard: `StudentDashboardView.swift`, `TutorDashboardView.swift`
- Features/Assessment: `AssessmentView.swift`, `CreateTestConfigView.swift`, `PerformanceAnalyticsView.swift`
- Features/Quiz: `CreateQuizView.swift`, `QuizReviewView.swift`, `AddQuestionView.swift`, `QuizAnalyticsView.swift`
- Utils: `ViewModelExtensions.swift`

**Modified Files:**
- `composeApp/src/androidMain/kotlin/com/jetbrains/kmpapp/App.kt` - Now uses shared routes
- `shared/src/iosMain/kotlin/com/jetbrains/kmpapp/KoinDependencies.kt` - Added ViewModels
- `iosApp/iosApp/iOSApp.swift` - Now uses GyansarNavigator

### Next Steps (Manual)

1. **Open Xcode** and add the new Swift files to the project
2. **Fix Xcode environment** - The xcrun error indicates Xcode command line tools need attention
3. **Test on iOS Simulator** - Run the app to verify navigation and UI
4. **Connect Kotlin Flows** - Implement proper Flow observation for real-time state updates
5. **Clean up old files** - Remove `ListView.swift` and `DetailView.swift` (museum-related)
