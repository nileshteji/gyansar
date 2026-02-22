import SwiftUI
import Shared

struct CreateQuizView: View {
    let onBack: () -> Void
    let onCreate: (Int64) -> Void
    
    @EnvironmentObject private var theme: GyansarTheme
    
    @State private var title: String = ""
    @State private var subject: String = ""
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                headerRow
                titleField
                subjectField
                createButton
            }
        }
        .background(theme.background)
        .navigationBarHidden(true)
    }
    
    private var headerRow: some View {
        HStack(spacing: 8) {
            Button(action: onBack) {
                Text("<-")
                    .font(GyansarTypography.titleLarge)
                    .foregroundColor(theme.tertiary)
            }
            Text("Create Quiz")
                .font(GyansarTypography.titleLarge)
                .foregroundColor(theme.onBackground)
        }
    }
    
    private var titleField: some View {
        VStack(alignment: .leading, spacing: 8) {
            SectionLabelView(text: "Quiz Title")
            TextField("", text: $title)
                .textFieldStyle(.roundedBorder)
        }
    }
    
    private var subjectField: some View {
        VStack(alignment: .leading, spacing: 8) {
            SectionLabelView(text: "Subject")
            TextField("", text: $subject)
                .textFieldStyle(.roundedBorder)
        }
    }
    
    private var createButton: some View {
        PrimaryButtonView(text: "Create Quiz") {
            if !title.isEmpty && !subject.isEmpty {
                onCreate(1)
            }
        }
    }
}
