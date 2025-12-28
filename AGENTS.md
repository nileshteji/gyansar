# AGENTS.md

## Build & Test Commands

- **Build**: `./gradlew build`
- **Run tests**: `./gradlew test`
- **Build Android app**: `./gradlew :composeApp:assembleDebug`
- **Clean**: `./gradlew clean`

## Codebase Structure

Kotlin Multiplatform (KMP) project with shared business logic and platform-specific UIs:

- **shared/**: Multiplatform library (Android/iOS). Contains API integration (Ktor), data models, repositories, ViewModels, and DI setup via Koin
- **composeApp/**: Android app with Jetpack Compose UI
- **iosApp/**: iOS app with SwiftUI
- **External APIs**: The Metropolitan Museum of Art Collection API for museum data
- **Key Dependencies**: Ktor (networking), kotlinx.serialization (JSON), Koin (DI), KMP-ObservableViewModel (shared VMs)

## Code Style Guidelines

- **Language**: Kotlin with official code style (`kotlin.code.style=official` in gradle.properties)
- **JVM Target**: Java 11
- **No star imports** (configured in .editorconfig with `ij_kotlin_name_count_to_use_star_import = 2147483647`)
- **Naming**: PascalCase for classes/composables, camelCase for functions/properties, SCREAMING_SNAKE_CASE for constants
- **Architecture**: MVVM with shared ViewModels using KMP-ObservableViewModel; repositories use coroutines and Flows
- **Imports**: Organize by package hierarchy; no unused imports
- **Error Handling**: Use try-catch with proper exception context; prefer returning null or sealed Result types
- **Composables**: Name with PascalCase, mark with `@Composable`, avoid nested lambdas deeper than 2 levels
- **Formatting**: Apply Kotlin formatter automatically; 4-space indentation
