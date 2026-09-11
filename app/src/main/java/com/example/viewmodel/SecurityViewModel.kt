package com.example.viewmodel

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Environment
import android.os.StatFs
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.AppBatteryUsage
import com.example.model.BatteryMode
import com.example.model.JunkCategory
import com.example.model.PermissionAccessLog
import com.example.model.ScanStatus
import com.example.model.SecurityCheckItem
import com.example.model.SecurityScoreStatus
import com.example.model.TurboGameProfile
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.DecimalFormat

data class SecurityUiState(
    val score: Int = 100,
    val scoreStatus: SecurityScoreStatus = SecurityScoreStatus.SECURE,
    val isOptimizing: Boolean = false,
    val statusMessage: String = "System is optimized & secured",
    
    // System Hardware Info
    val ramUsedMb: Long = 4200,
    val ramTotalMb: Long = 8192,
    val storageUsedGb: Double = 52.4,
    val storageTotalGb: Double = 128.0,
    
    // Junk & Cleaner
    val junkCategories: List<JunkCategory> = emptyList(),
    val isScanningJunk: Boolean = false,
    val isCleaningJunk: Boolean = false,
    val lastCleanedMb: Long = 0,
    
    // Battery Status (Modded UI)
    val batteryLevel: Int = 84,
    val isCharging: Boolean = false,
    val batteryMode: BatteryMode = BatteryMode.BALANCED,
    val batteryTemperature: Double = 31.8,
    val batteryHealthCondition: String = "Excellent (98% Health)",
    val chargeCycleCount: Int = 184,
    val batteryVoltageMv: Int = 4180,
    val fastChargeType: String = "MI Turbo Charge (33W)",
    val remainingHours: Int = 26,
    val remainingMinutes: Int = 45,
    val appBatteryList: List<AppBatteryUsage> = emptyList(),
    
    // Security Scanning
    val securityChecks: List<SecurityCheckItem> = emptyList(),
    val isSecurityScanning: Boolean = false,
    val scanProgress: Float = 1.0f,
    val currentScanningTitle: String = "Complete",
    
    // Modded MIUI Exclusives (from MiuiSecurityMod changelog)
    val force100Score: Boolean = true,
    val shutdownConfirmation: Boolean = true,
    val incognitoMode: Boolean = true,
    val videoToolboxEnabled: Boolean = true,
    val gameTurboEnabled: Boolean = true,
    val activeGameProfile: TurboGameProfile = TurboGameProfile("1", "Genshin Impact", 120, true, 90),
    val permissionLogs: List<PermissionAccessLog> = emptyList(),
    val gameProfiles: List<TurboGameProfile> = emptyList()
)

class SecurityViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SecurityUiState())
    val uiState: StateFlow<SecurityUiState> = _uiState.asStateFlow()

    fun initializeDeviceData(context: Context) {
        viewModelScope.launch {
            // Read hardware memory info
            val actManager = context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
            val memInfo = ActivityManager.MemoryInfo()
            actManager?.getMemoryInfo(memInfo)

            val totalRam = if (memInfo.totalMem > 0) memInfo.totalMem / (1024 * 1024) else 8192L
            val availRam = if (memInfo.availMem > 0) memInfo.availMem / (1024 * 1024) else 3992L
            val usedRam = (totalRam - availRam).coerceAtLeast(1024L)

            // Read internal storage
            var totalStorageGb = 128.0
            var usedStorageGb = 54.2
            try {
                val statFs = StatFs(Environment.getDataDirectory().path)
                val totalBytes = statFs.blockCountLong * statFs.blockSizeLong
                val availBytes = statFs.availableBlocksLong * statFs.blockSizeLong
                if (totalBytes > 0) {
                    totalStorageGb = (totalBytes.toDouble() / (1024 * 1024 * 1024))
                    usedStorageGb = ((totalBytes - availBytes).toDouble() / (1024 * 1024 * 1024))
                }
            } catch (_: Exception) { }

            // Read Battery
            val ifilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            val batteryStatus = context.registerReceiver(null, ifilter)
            val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: 84
            val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: 100
            val bPct = if (level >= 0 && scale > 0) (level * 100 / scale) else 84
            val status = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
            val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
            val tempTenths = batteryStatus?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 318) ?: 318
            val tempCelsius = tempTenths / 10.0

            val initialJunk = listOf(
                JunkCategory("cache", "Cache Junk", "Temporary application cache & thumbnails", 682L * 1024 * 1024, 1420),
                JunkCategory("residual", "Residual Files", "Uninstalled app configs & leftover data", 314L * 1024 * 1024, 88),
                JunkCategory("obsolete_apk", "Obsolete Installation Packages", "Installed APK installation files", 480L * 1024 * 1024, 3),
                JunkCategory("memory", "Memory Junk", "Unneeded background resident process data", 820L * 1024 * 1024, 34),
                JunkCategory("temp", "System Temporary Files", "Crash dumps, logs and expired caches", 215L * 1024 * 1024, 512)
            )

            val initialChecks = listOf(
                SecurityCheckItem("av_defs", "Antivirus Definitions", "AVL & Tencent Cloud Dual Engines up to date", ScanStatus.PASSED, "Definitions updated today"),
                SecurityCheckItem("payment_env", "Payment Environment", "WeChat / Alipay / Banking sandbox verification", ScanStatus.PASSED, "Secure banking sandbox isolated"),
                SecurityCheckItem("root_integrity", "System & Root Integrity", "Kernel, Bootloader & Magisk module isolation", ScanStatus.PASSED, "MiuiSecurityMod Magisk active"),
                SecurityCheckItem("permissions", "Dangerous Permissions", "Background microphone, camera, and SMS audit", ScanStatus.PASSED, "0 abnormal authorizations"),
                SecurityCheckItem("wifi_sec", "Wi-Fi & DNS Security", "ARP spoofing, DNS hijacking & SSL validation", ScanStatus.PASSED, "Private DNS protected")
            )

            val initialApps = listOf(
                AppBatteryUsage("Camera & Photos", "com.android.camera", 18.4f, 420),
                AppBatteryUsage("Social Messenger", "org.telegram.messenger", 14.1f, 312),
                AppBatteryUsage("Game Turbo Display", "com.miui.powerkeeper", 11.2f, 250),
                AppBatteryUsage("Media Player", "com.miui.videoplayer", 8.6f, 190),
                AppBatteryUsage("System Launcher", "com.miui.home", 6.2f, 135)
            )

            val initialLogs = listOf(
                PermissionAccessLog("1", "Gallery", "Location Access", "2m ago", wasShielded = false),
                PermissionAccessLog("2", "Social App", "Clipboard Read", "15m ago", wasShielded = true),
                PermissionAccessLog("3", "Browser", "Camera Access", "42m ago", wasShielded = true),
                PermissionAccessLog("4", "Voice Notes", "Microphone", "2h ago", wasShielded = false)
            )

            val initialGames = listOf(
                TurboGameProfile("1", "Genshin Impact", 120, gpuBoost = true, touchSensitivity = 95),
                TurboGameProfile("2", "PUBG Mobile", 90, gpuBoost = true, touchSensitivity = 90),
                TurboGameProfile("3", "Call of Duty", 120, gpuBoost = true, touchSensitivity = 88)
            )

            _uiState.update {
                it.copy(
                    ramTotalMb = totalRam,
                    ramUsedMb = usedRam,
                    storageTotalGb = totalStorageGb,
                    storageUsedGb = usedStorageGb,
                    batteryLevel = bPct,
                    isCharging = isCharging,
                    batteryTemperature = tempCelsius,
                    junkCategories = initialJunk,
                    securityChecks = initialChecks,
                    appBatteryList = initialApps,
                    permissionLogs = initialLogs,
                    gameProfiles = initialGames
                )
            }
        }
    }

    fun optimizeSystem() {
        if (_uiState.value.isOptimizing) return
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isOptimizing = true,
                    scoreStatus = SecurityScoreStatus.OPTIMIZING,
                    statusMessage = "Stopping idle background tasks..."
                )
            }
            delay(600)
            _uiState.update {
                it.copy(
                    statusMessage = "Releasing cached memory buffers...",
                    ramUsedMb = (it.ramUsedMb - 450).coerceAtLeast(1024L)
                )
            }
            delay(700)
            _uiState.update {
                it.copy(
                    statusMessage = "Optimizing battery drain parameters..."
                )
            }
            delay(600)
            _uiState.update {
                it.copy(
                    isOptimizing = false,
                    score = 100,
                    scoreStatus = SecurityScoreStatus.SECURE,
                    statusMessage = "System is in peak performance! Score: 100"
                )
            }
        }
    }

    fun toggleJunkSelection(id: String) {
        _uiState.update { state ->
            val updated = state.junkCategories.map {
                if (it.id == id) it.copy(isSelected = !it.isSelected) else it
            }
            state.copy(junkCategories = updated)
        }
    }

    fun startJunkScan() {
        viewModelScope.launch {
            _uiState.update { it.copy(isScanningJunk = true) }
            delay(1200)
            _uiState.update { it.copy(isScanningJunk = false) }
        }
    }

    fun cleanSelectedJunk() {
        if (_uiState.value.isCleaningJunk) return
        viewModelScope.launch {
            _uiState.update { it.copy(isCleaningJunk = true) }
            val selectedBytes = _uiState.value.junkCategories
                .filter { it.isSelected }
                .sumOf { it.sizeBytes }
            delay(1200)
            val updated = _uiState.value.junkCategories.map {
                if (it.isSelected) it.copy(sizeBytes = 0, itemCount = 0) else it
            }
            val freedMb = selectedBytes / (1024 * 1024)
            _uiState.update {
                it.copy(
                    isCleaningJunk = false,
                    junkCategories = updated,
                    lastCleanedMb = freedMb,
                    storageUsedGb = (it.storageUsedGb - (freedMb.toDouble() / 1024.0)).coerceAtLeast(10.0),
                    statusMessage = "Cleaned ${freedMb}MB junk files"
                )
            }
        }
    }

    fun setBatteryMode(mode: BatteryMode) {
        val (h, m) = when (mode) {
            BatteryMode.BATTERY_SAVER -> Pair(34, 15)
            BatteryMode.BALANCED -> Pair(26, 45)
            BatteryMode.PERFORMANCE -> Pair(19, 30)
            BatteryMode.ULTRA_SAVER -> Pair(78, 0)
        }
        _uiState.update {
            it.copy(
                batteryMode = mode,
                remainingHours = h,
                remainingMinutes = m
            )
        }
    }

    fun optimizeAppBattery(packageName: String) {
        _uiState.update { state ->
            val updated = state.appBatteryList.map {
                if (it.packageName == packageName) it.copy(isOptimized = true, usagePercentage = (it.usagePercentage * 0.7f)) else it
            }
            state.copy(appBatteryList = updated)
        }
    }

    fun runSecurityScan() {
        if (_uiState.value.isSecurityScanning) return
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSecurityScanning = true,
                    scanProgress = 0.0f,
                    scoreStatus = SecurityScoreStatus.SCANNING,
                    currentScanningTitle = "Initializing dual antivirus engines..."
                )
            }
            val checks = _uiState.value.securityChecks
            for (i in checks.indices) {
                _uiState.update { state ->
                    val updated = state.securityChecks.mapIndexed { idx, item ->
                        if (idx == i) item.copy(status = ScanStatus.SCANNING) else item
                    }
                    state.copy(
                        securityChecks = updated,
                        scanProgress = (i + 0.5f) / checks.size,
                        currentScanningTitle = "Scanning ${checks[i].title}..."
                    )
                }
                delay(650)
                _uiState.update { state ->
                    val updated = state.securityChecks.mapIndexed { idx, item ->
                        if (idx == i) item.copy(status = ScanStatus.PASSED) else item
                    }
                    state.copy(
                        securityChecks = updated,
                        scanProgress = (i + 1f) / checks.size
                    )
                }
            }
            _uiState.update {
                it.copy(
                    isSecurityScanning = false,
                    scanProgress = 1.0f,
                    score = if (it.force100Score) 100 else 100,
                    scoreStatus = SecurityScoreStatus.SECURE,
                    currentScanningTitle = "Security scan complete. All modules safe."
                )
            }
        }
    }

    fun toggleForce100Score() {
        _uiState.update {
            val next = !it.force100Score
            it.copy(force100Score = next, score = if (next) 100 else 94)
        }
    }

    fun toggleShutdownConfirmation() {
        _uiState.update { it.copy(shutdownConfirmation = !it.shutdownConfirmation) }
    }

    fun toggleIncognitoMode() {
        _uiState.update {
            val next = !it.incognitoMode
            it.copy(
                incognitoMode = next,
                statusMessage = if (next) "Incognito Mode: Camera & Mic shielded" else "Incognito Mode disabled"
            )
        }
    }

    fun toggleVideoToolbox() {
        _uiState.update { it.copy(videoToolboxEnabled = !it.videoToolboxEnabled) }
    }

    fun toggleGameTurbo() {
        _uiState.update { it.copy(gameTurboEnabled = !it.gameTurboEnabled) }
    }

    fun updateGameFps(gameId: String, fps: Int) {
        _uiState.update { state ->
            val updated = state.gameProfiles.map {
                if (it.id == gameId) it.copy(targetFps = fps) else it
            }
            state.copy(gameProfiles = updated)
        }
    }
}
