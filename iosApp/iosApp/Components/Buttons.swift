import SwiftUI

struct PrimaryButtonView: View {
    let text: String
    let action: () -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        Button(action: action) {
            Text(text)
                .font(GyansarTypography.labelLarge)
                .foregroundColor(theme.onPrimary)
                .frame(maxWidth: .infinity)
                .frame(height: 52)
                .background(theme.primary)
                .cornerRadius(16)
        }
    }
}

struct SecondaryButtonView: View {
    let text: String
    let action: () -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        Button(action: action) {
            Text(text)
                .font(GyansarTypography.labelLarge)
                .foregroundColor(theme.tertiary)
                .frame(maxWidth: .infinity)
                .frame(height: 52)
                .background(theme.background)
                .overlay(
                    RoundedRectangle(cornerRadius: 16)
                        .stroke(theme.outline, lineWidth: 1)
                )
                .cornerRadius(16)
        }
    }
}

struct FABView: View {
    let action: () -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        Button(action: action) {
            Text("+")
                .font(GyansarTypography.titleLarge)
                .foregroundColor(theme.onPrimary)
                .frame(width: 56, height: 56)
                .background(theme.primary)
                .clipShape(Circle())
        }
        .shadow(color: .black.opacity(0.2), radius: 4, x: 0, y: 2)
    }
}

struct GoogleSignInButtonView: View {
    let text: String
    let action: () -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        Button(action: action) {
            HStack(spacing: 12) {
                ZStack {
                    Circle()
                        .fill(Color(hex: 0x4285F4))
                        .frame(width: 28, height: 28)
                    Text("G")
                        .font(GyansarTypography.labelLarge)
                        .foregroundColor(.white)
                }
                Text(text)
                    .font(GyansarTypography.labelLarge)
                    .foregroundColor(theme.onBackground)
            }
            .frame(maxWidth: .infinity)
            .frame(height: 52)
            .background(theme.background)
            .overlay(
                RoundedRectangle(cornerRadius: 16)
                    .stroke(theme.outline, lineWidth: 1)
            )
            .cornerRadius(16)
        }
    }
}
