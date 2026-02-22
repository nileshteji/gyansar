import SwiftUI

class GyansarTheme: ObservableObject {
    @Published var colorScheme: ColorScheme = .light
    
    var primary: Color { colorScheme == .dark ? GyansarColors.Dark.primary : GyansarColors.Light.primary }
    var onPrimary: Color { colorScheme == .dark ? GyansarColors.Dark.onPrimary : GyansarColors.Light.onPrimary }
    var secondary: Color { colorScheme == .dark ? GyansarColors.Dark.secondary : GyansarColors.Light.secondary }
    var onSecondary: Color { colorScheme == .dark ? GyansarColors.Dark.onSecondary : GyansarColors.Light.onSecondary }
    var tertiary: Color { colorScheme == .dark ? GyansarColors.Dark.tertiary : GyansarColors.Light.tertiary }
    var onTertiary: Color { colorScheme == .dark ? GyansarColors.Dark.onTertiary : GyansarColors.Light.onTertiary }
    var background: Color { colorScheme == .dark ? GyansarColors.Dark.background : GyansarColors.Light.background }
    var onBackground: Color { colorScheme == .dark ? GyansarColors.Dark.onBackground : GyansarColors.Light.onBackground }
    var surface: Color { colorScheme == .dark ? GyansarColors.Dark.surface : GyansarColors.Light.surface }
    var onSurface: Color { colorScheme == .dark ? GyansarColors.Dark.onSurface : GyansarColors.Light.onSurface }
    var surfaceVariant: Color { colorScheme == .dark ? GyansarColors.Dark.surfaceVariant : GyansarColors.Light.surfaceVariant }
    var onSurfaceVariant: Color { colorScheme == .dark ? GyansarColors.Dark.onSurfaceVariant : GyansarColors.Light.onSurfaceVariant }
    var outline: Color { colorScheme == .dark ? GyansarColors.Dark.outline : GyansarColors.Light.outline }
    var error: Color { colorScheme == .dark ? GyansarColors.Dark.error : GyansarColors.Light.error }
    var onError: Color { colorScheme == .dark ? GyansarColors.Dark.onError : GyansarColors.Light.onError }
    
    func heroBrush() -> LinearGradient {
        LinearGradient(
            colors: [background, surface],
            startPoint: .top,
            endPoint: .bottom
        )
    }
}

struct GyansarThemeKey: EnvironmentKey {
    static let defaultValue = GyansarTheme()
}

extension EnvironmentValues {
    var gyansarTheme: GyansarTheme {
        get { self[GyansarThemeKey.self] }
        set { self[GyansarThemeKey.self] = newValue }
    }
}

struct ThemedView<Content: View>: View {
    @Environment(\.colorScheme) private var systemColorScheme
    @StateObject private var theme = GyansarTheme()
    let content: (GyansarTheme) -> Content
    
    var body: some View {
        content(theme)
            .environment(\.gyansarTheme, theme)
            .onAppear {
                theme.colorScheme = systemColorScheme
            }
            .onChange(of: systemColorScheme) { newScheme in
                theme.colorScheme = newScheme
            }
    }
}
