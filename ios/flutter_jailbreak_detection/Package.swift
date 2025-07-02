// swift-tools-version: 5.9
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "flutter_jailbreak_detection",
    platforms: [
        .iOS("12.0"),
    ],
    products: [
        .library(name: "flutter-jailbreak-detection", targets: ["flutter_jailbreak_detection"])
    ],
    dependencies: [
        .package(url: "https://github.com/securing/IOSSecuritySuite.git", from: "1.9.11")
    ],
    targets: [
        .target(
            name: "flutter_jailbreak_detection",
            dependencies: [
                "IOSSecuritySuite"
            ],
            resources: []
        )
    ]
)
