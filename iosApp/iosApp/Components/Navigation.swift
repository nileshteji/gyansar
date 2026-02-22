import SwiftUI

struct BottomNavView: View {
    let items: [String]
    let selectedIndex: Int
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        HStack {
            ForEach(Array(items.enumerated()), id: \.offset) { index, label in
                NavItemView(label: label, selected: index == selectedIndex)
                    .frame(maxWidth: .infinity)
            }
        }
        .padding(.vertical, 8)
        .background(theme.surface)
        .overlay(
            RoundedRectangle(cornerRadius: 18)
                .stroke(theme.outline, lineWidth: 1)
        )
        .cornerRadius(18)
    }
}

struct NavItemView: View {
    let label: String
    let selected: Bool
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        let background = selected ? theme.primary : theme.surfaceVariant
        let textColor = selected ? theme.onPrimary : theme.onSurfaceVariant
        
        VStack(spacing: 4) {
            Circle()
                .fill(background)
                .frame(width: 28, height: 28)
                .overlay(
                    Image(systemName: iconForLabel(label))
                        .font(.system(size: 14))
                        .foregroundColor(textColor)
                )
            Text(label)
                .font(GyansarTypography.labelMedium)
                .foregroundColor(textColor)
        }
    }
    
    private func iconForLabel(_ label: String) -> String {
        switch label.lowercased() {
        case "home": return "house.fill"
        case "practice": return "book.fill"
        case "profile": return "person.fill"
        case "settings": return "gear"
        default: return "circle.fill"
        }
    }
}

struct SectionLabelView: View {
    let text: String
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        Text(text)
            .font(GyansarTypography.labelLarge)
            .foregroundColor(theme.tertiary)
    }
}
