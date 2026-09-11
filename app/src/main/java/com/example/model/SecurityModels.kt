package com.example.model

enum class SecurityScoreStatus {
    SECURE,
    OPTIMIZING,
    SCANNING,
    ATTENTION
}

enum class BatteryMode {
    BATTERY_SAVER,
    BALANCED,
    PERFORMANCE,
    ULTRA_SAVER
}

data class JunkCategory(
    val id: String,
    val title: String,
    val description: String,
    val sizeBytes: Long,
    val itemCount: Int,
    val isSelected: Boolean = true
)

data class AppBatteryUsage(
    val appName: String,
    val packageName: String,
    val usagePercentage: Float,
    val drainMah: Int,
    val isOptimized: Boolean = true
)

enum class ScanStatus {
    PENDING,
    SCANNING,
    PASSED,
    ALERT
}

data class SecurityCheckItem(
    val id: String,
    val title: String,
    val description: String,
    val status: ScanStatus = ScanStatus.PASSED,
    val detailMessage: String = "Passed security check"
)

data class PermissionAccessLog(
    val id: String,
    val appName: String,
    val permission: String,
    val timeAgo: String,
    val wasShielded: Boolean = false
)

data class TurboGameProfile(
    val id: String,
    val name: String,
    val targetFps: Int = 120,
    val gpuBoost: Boolean = true,
    val touchSensitivity: Int = 85
)
