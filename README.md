# Security Center (MIUI Security Mod)

An Android application built with Kotlin and Jetpack Compose, faithfully recreating the functionality, aesthetic, and modded exclusive features of **MiuiSecurityMod** (originally created by Kashi).

## Features

- **Security Center Dashboard**:
  - Circular animated security gauge with real-time score optimization
  - Live hardware metrics monitoring: RAM usage, internal storage capacity, and battery status
  - Quick-action bento grid: Cleaner, Security Scan, Battery & Performance, Boost Speed, Deep Clean, and Manage Apps

- **Cleaner & Cache Optimizer**:
  - Scans trash cache, residual uninstalled files, obsolete APK packages, memory processes, and temporary system files
  - Interactive category selection with item count and byte calculation
  - One-tap cleanup with immediate storage recovery feedback

- **Battery & Performance (Modded UI)**:
  - 4-way performance profiles: Save Battery, Balanced, Performance, and Ultra Saver
  - Dynamic estimated remaining time projection
  - Detailed hardware battery metrics: temperature in °C, battery health rating, charge cycle counter, and voltage
  - App battery consumption breakdown with individual optimization actions

- **Deep Security Scan**:
  - Antivirus definitions audit (dual engine verification)
  - Payment sandbox security check
  - System and root integrity verification
  - Dangerous background permissions monitoring
  - Wi-Fi and DNS security validation

- **MiuiSecurityMod Exclusives & Toolbox**:
  - **Force 100 Security Score**: Preserves the modded 100-point security rating
  - **Shutdown Confirmation**: Prevents unauthorized power-offs from the lockscreen
  - **Global Incognito Mode**: One-tap sensor shield for microphone, camera, and clipboard access
  - **Video Toolbox**: Screen-off audio playback and dynamic display enhancements
  - **Game Turbo 5.0**: Performance profile manager with target FPS selectors (60/90/120 Hz) and GPU boost
  - **Privacy Behavior Log**: Timeline of recent sensitive permissions used by apps

## Tech Stack

- **Platform**: Android (minSdk 26, targetSdk 36)
- **UI Framework**: Jetpack Compose with Material 3
- **Language**: Kotlin 2.2
- **Build System**: Gradle 9.3 with Kotlin DSL
