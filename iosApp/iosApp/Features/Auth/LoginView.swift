import SwiftUI
import Shared

struct LoginView: View {
    let onSignIn: () -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    
    private let state = GyansarWireframeData.shared.gallery.login
    
    var body: some View {
        ZStack {
            theme.heroBrush()
                .ignoresSafeArea()
            
            Circle()
                .fill(theme.primary.opacity(0.2))
                .frame(width: 140, height: 140)
                .offset(x: 120, y: -280)
            
            Circle()
                .fill(theme.secondary.opacity(0.18))
                .frame(width: 120, height: 120)
                .offset(x: -120, y: 100)
            
            VStack(spacing: 0) {
                Spacer().frame(height: 12)
                
                GyansarLogoView()
                
                Spacer().frame(height: 12)
                
                Text(state.tagline)
                    .font(GyansarTypography.bodyLarge)
                    .foregroundColor(theme.onBackground)
                    .multilineTextAlignment(.center)
                
                Spacer()
                
                GoogleSignInButtonView(text: state.buttonText, action: onSignIn)
                
                Spacer().frame(height: 16)
                
                Text(state.termsText)
                    .font(GyansarTypography.labelMedium)
                    .foregroundColor(theme.onBackground.opacity(0.7))
                    .multilineTextAlignment(.center)
            }
            .padding(.horizontal, 20)
            .padding(.vertical, 24)
        }
        .navigationBarHidden(true)
    }
}

struct GyansarLogoView: View {
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        HStack(spacing: 12) {
            ZStack {
                Circle()
                    .fill(
                        LinearGradient(
                            colors: [theme.primary, theme.tertiary],
                            startPoint: .topLeading,
                            endPoint: .bottomTrailing
                        )
                    )
                    .frame(width: 48, height: 48)
                
                Text("G")
                    .font(GyansarTypography.titleLarge)
                    .foregroundColor(theme.onPrimary)
            }
            
            Text("Gyansar")
                .font(GyansarTypography.headlineMedium)
                .foregroundColor(theme.onBackground)
        }
    }
}
