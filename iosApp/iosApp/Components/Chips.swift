import SwiftUI

struct AvatarChipView: View {
    let initials: String
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        Text(initials)
            .font(GyansarTypography.labelLarge)
            .foregroundColor(theme.onSecondary)
            .frame(width: 44, height: 44)
            .background(theme.secondary)
            .clipShape(Circle())
    }
}

struct TagChipView: View {
    let text: String
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        Text(text)
            .font(GyansarTypography.labelMedium)
            .foregroundColor(theme.tertiary)
            .padding(.horizontal, 10)
            .padding(.vertical, 4)
            .background(theme.surfaceVariant)
            .cornerRadius(50)
    }
}

struct HintPillView: View {
    let text: String
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        Text(text)
            .font(GyansarTypography.labelMedium)
            .foregroundColor(theme.onSecondary)
            .padding(.horizontal, 12)
            .padding(.vertical, 6)
            .background(theme.secondary)
            .cornerRadius(16)
    }
}

struct TabPillRowView: View {
    let options: [String]
    let selectedIndex: Int
    let onSelect: (Int) -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        HStack(spacing: 0) {
            ForEach(Array(options.enumerated()), id: \.offset) { index, option in
                let selected = index == selectedIndex
                let background = selected ? theme.primary : Color.clear
                let textColor = selected ? theme.onPrimary : theme.onSurfaceVariant
                
                Button(action: { onSelect(index) }) {
                    Text(option)
                        .font(GyansarTypography.labelMedium)
                        .foregroundColor(textColor)
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 8)
                        .background(background)
                        .cornerRadius(14)
                }
            }
        }
        .padding(4)
        .background(theme.surfaceVariant)
        .cornerRadius(18)
    }
}

struct AnswerOptionView: View {
    let text: String
    let selected: Bool
    let action: () -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        Button(action: action) {
            HStack(spacing: 12) {
                Circle()
                    .stroke(selected ? theme.primary : theme.outline, lineWidth: 2)
                    .background(
                        Circle()
                            .fill(selected ? theme.primary : Color.clear)
                            .padding(4)
                    )
                    .frame(width: 24, height: 24)
                
                Text(text)
                    .font(GyansarTypography.bodyMedium)
                    .foregroundColor(theme.onSurface)
                
                Spacer()
            }
            .padding(14)
            .background(selected ? theme.surfaceVariant : theme.surface)
            .overlay(
                RoundedRectangle(cornerRadius: 12)
                    .stroke(selected ? theme.primary : theme.outline, lineWidth: 1)
            )
            .cornerRadius(12)
        }
        .buttonStyle(.plain)
    }
}

struct AnnouncementItemView: View {
    let text: String
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        HStack(alignment: .top, spacing: 8) {
            Circle()
                .fill(theme.secondary)
                .frame(width: 10, height: 10)
                .padding(.top, 4)
            Text(text)
                .font(GyansarTypography.bodyMedium)
                .foregroundColor(theme.onSurface)
        }
        .padding(.vertical, 8)
    }
}
