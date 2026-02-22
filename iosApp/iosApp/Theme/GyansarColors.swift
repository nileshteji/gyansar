import SwiftUI

struct GyansarColors {
    // Light Theme Colors
    struct Light {
        static let primary = Color(hex: 0x15803D)
        static let onPrimary = Color.white
        static let secondary = Color(hex: 0x84CC16)
        static let onSecondary = Color.white
        static let tertiary = Color(hex: 0x10B981)
        static let onTertiary = Color.white
        static let background = Color.white
        static let onBackground = Color(hex: 0x374151)
        static let surface = Color(hex: 0xF0FDF4)
        static let onSurface = Color(hex: 0x374151)
        static let surfaceVariant = Color(hex: 0xF0FDF4)
        static let onSurfaceVariant = Color(hex: 0x374151)
        static let outline = Color(hex: 0xE5E7EB)
        static let error = Color(hex: 0xFF4D4D)
        static let onError = Color.white
    }
    
    // Dark Theme Colors
    struct Dark {
        static let primary = Color(hex: 0x22C55E)
        static let onPrimary = Color(hex: 0x0F172A)
        static let secondary = Color(hex: 0xA3E635)
        static let onSecondary = Color(hex: 0x0F172A)
        static let tertiary = Color(hex: 0x10B981)
        static let onTertiary = Color(hex: 0x0F172A)
        static let background = Color(hex: 0x0F172A)
        static let onBackground = Color(hex: 0xF8FAFC)
        static let surface = Color(hex: 0x1E293B)
        static let onSurface = Color(hex: 0xF8FAFC)
        static let surfaceVariant = Color(hex: 0x1E293B)
        static let onSurfaceVariant = Color(hex: 0x94A3B8)
        static let outline = Color(hex: 0x334155)
        static let error = Color(hex: 0xEF4444)
        static let onError = Color(hex: 0xF8FAFC)
    }
}

extension Color {
    init(hex: UInt, alpha: Double = 1.0) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xFF) / 255.0,
            green: Double((hex >> 8) & 0xFF) / 255.0,
            blue: Double(hex & 0xFF) / 255.0,
            opacity: alpha
        )
    }
}
