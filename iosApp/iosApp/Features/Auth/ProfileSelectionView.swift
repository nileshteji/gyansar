import SwiftUI
import Shared

struct ProfileSelectionView: View {
    let onStudentClick: () -> Void
    let onTutorClick: () -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    @EnvironmentObject private var navigator: GyansarNavigatorState
    
    private let state = GyansarWireframeData.shared.gallery.profileSelection
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                HStack(spacing: 8) {
                    Button(action: { navigator.popBack() }) {
                        Text("<-")
                            .font(GyansarTypography.titleLarge)
                            .foregroundColor(theme.tertiary)
                    }
                    Text(state.header)
                        .font(GyansarTypography.titleLarge)
                        .foregroundColor(theme.onBackground)
                }
                
                ForEach(Array(state.options.enumerated()), id: \.offset) { index, option in
                    ProfileCardView(
                        option: option,
                        action: index == 0 ? onStudentClick : onTutorClick
                    )
                }
            }
            .padding(4)
        }
        .background(theme.background)
        .navigationBarHidden(true)
    }
}

struct ProfileCardView: View {
    let option: ProfileOptionState
    let action: () -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        Button(action: action) {
            VStack(alignment: .leading, spacing: 8) {
                HStack(spacing: 12) {
                    ZStack {
                        Circle()
                            .fill(theme.surfaceVariant)
                            .frame(width: 44, height: 44)
                        
                        Text(String(option.badge.prefix(1)))
                            .font(GyansarTypography.titleMedium)
                            .foregroundColor(theme.tertiary)
                    }
                    
                    Text(option.title)
                        .font(GyansarTypography.titleMedium)
                        .foregroundColor(theme.onSurface)
                }
                
                Text(option.description_)
                    .font(GyansarTypography.bodyMedium)
                    .foregroundColor(theme.onBackground.opacity(0.7))
                    .multilineTextAlignment(.leading)
            }
            .padding(16)
            .frame(maxWidth: .infinity, alignment: .leading)
            .background(theme.surface)
            .overlay(
                RoundedRectangle(cornerRadius: 20)
                    .stroke(theme.outline, lineWidth: 1)
            )
            .cornerRadius(20)
        }
        .buttonStyle(.plain)
    }
}
