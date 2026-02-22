import SwiftUI

struct HistogramView: View {
    let bars: [Float]
    let labels: [String]
    
    @EnvironmentObject private var theme: GyansarTheme
    
    var body: some View {
        VStack(spacing: 6) {
            HStack(alignment: .bottom, spacing: 6) {
                ForEach(Array(bars.enumerated()), id: \.offset) { _, fraction in
                    RoundedRectangle(cornerRadius: 6)
                        .fill(theme.primary)
                        .frame(maxWidth: .infinity)
                        .frame(height: CGFloat(fraction) * 90)
                }
            }
            .frame(height: 90)
            
            HStack {
                ForEach(labels, id: \.self) { label in
                    Text(label)
                        .font(GyansarTypography.labelMedium)
                        .foregroundColor(theme.onBackground.opacity(0.6))
                        .frame(maxWidth: .infinity)
                }
            }
        }
    }
}
