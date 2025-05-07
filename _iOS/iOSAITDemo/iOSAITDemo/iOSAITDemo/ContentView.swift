//
//  ContentView.swift
//  iOSAITDemo
//
//  Created by Peter Ekler on 2025. 05. 07..
//

import SwiftUI

struct ContentView: View {
    
    @State private var currentTime: String = ""
    
    var body: some View {
        VStack {
            
            Text("Current time: \(currentTime)")
            Button("Show time") {
                let formatter = DateFormatter()
                formatter.dateFormat = "HH:mm:ss"
                currentTime = formatter.string(from: Date())
            }
        }
        .padding()
    }
}

#Preview {
    ContentView()
}
