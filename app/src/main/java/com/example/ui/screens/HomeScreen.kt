package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.FolderSpecial
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SecurityMeter
import com.example.ui.theme.MiuiCyan
import com.example.ui.theme.MiuiOrange
import com.example.ui.theme.MiuiRed
import com.example.ui.theme.MiuiTeal
import com.example.viewmodel.SecurityUiState

@Composable
fun HomeScreen(
    state: SecurityUiState,
    onOptimizeClick: () -> Unit,
    onNavigateToCleaner: () -> Unit,
    onNavigateToBattery: () -> Unit,
    onNavigateToSecurity: () -> Unit,
    onNavigateToTools: () -> Unit,
    onToggleIncognito: () -> Unit,
    onToggleShutdownConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            // Hero Top Card with Circular Meter
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("security_hero_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xFF0F2636),
                                    Color(0xFF0F1A2A)
                                )
                            )
                        )
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Security Center",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "MIUI Security Mod • Android 13+",
                                fontSize = 11.sp,
                                color = MiuiCyan,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MiuiTeal.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "MOD UNLOCKED",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = MiuiTeal
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    SecurityMeter(
                        score = state.score,
                        status = state.scoreStatus,
                        isOptimizing = state.isOptimizing
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = state.statusMessage,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = onOptimizeClick,
                        enabled = !state.isOptimizing,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("optimize_button"),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MiuiTeal,
                            contentColor = Color.Black
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.FlashOn,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (state.isOptimizing) "Optimizing System..." else "Optimize System",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Real Hardware Metrics Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricChip(
                    icon = Icons.Default.Memory,
                    label = "RAM Used",
                    value = "${String.format("%.1f", state.ramUsedMb / 1024.0)} / ${state.ramTotalMb / 1024} GB",
                    color = MiuiCyan,
                    modifier = Modifier.weight(1f)
                )
                MetricChip(
                    icon = Icons.Default.Storage,
                    label = "Storage",
                    value = "${String.format("%.1f", state.storageUsedGb)} / ${state.storageTotalGb.toInt()} GB",
                    color = MiuiOrange,
                    modifier = Modifier.weight(1f)
                )
                MetricChip(
                    icon = Icons.Default.BatteryChargingFull,
                    label = "Battery",
                    value = "${state.batteryLevel}%",
                    color = MiuiTeal,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Quick Actions Bento Grid (Cleaner, Security Scan, Battery, Speed Boost, Deep Clean, Manage Apps)
        item {
            Text(
                text = "Security Services",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ActionBentoCard(
                        title = "Cleaner",
                        subtitle = "Clean junk cache",
                        icon = Icons.Default.CleaningServices,
                        accentColor = MiuiTeal,
                        onClick = onNavigateToCleaner,
                        modifier = Modifier.weight(1f),
                        testTag = "action_cleaner"
                    )
                    ActionBentoCard(
                        title = "Security Scan",
                        subtitle = "Antivirus & payment",
                        icon = Icons.Default.Shield,
                        accentColor = MiuiCyan,
                        onClick = onNavigateToSecurity,
                        modifier = Modifier.weight(1f),
                        testTag = "action_security_scan"
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ActionBentoCard(
                        title = "Battery",
                        subtitle = "${state.remainingHours}h ${state.remainingMinutes}m remaining",
                        icon = Icons.Default.BatteryChargingFull,
                        accentColor = MiuiOrange,
                        onClick = onNavigateToBattery,
                        modifier = Modifier.weight(1f),
                        testTag = "action_battery"
                    )
                    ActionBentoCard(
                        title = "Boost Speed",
                        subtitle = "RAM acceleration",
                        icon = Icons.Default.Speed,
                        accentColor = Color(0xFF8B5CF6),
                        onClick = onOptimizeClick,
                        modifier = Modifier.weight(1f),
                        testTag = "action_speed_boost"
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ActionBentoCard(
                        title = "Deep Clean",
                        subtitle = "Big files & APKs",
                        icon = Icons.Default.DeleteSweep,
                        accentColor = Color(0xFF06B6D4),
                        onClick = onNavigateToCleaner,
                        modifier = Modifier.weight(1f),
                        testTag = "action_deep_clean"
                    )
                    ActionBentoCard(
                        title = "Manage Apps",
                        subtitle = "Permissions & DND",
                        icon = Icons.Default.Apps,
                        accentColor = Color(0xFF10B981),
                        onClick = onNavigateToTools,
                        modifier = Modifier.weight(1f),
                        testTag = "action_manage_apps"
                    )
                }
            }
        }

        // MIUI Security Mod Exclusive Features Card
        item {
            Text(
                text = "MiuiSecurityMod Features",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    ModFeatureToggleRow(
                        title = "Incognito Mode",
                        subtitle = "Shield camera, microphone and clipboard globally",
                        icon = Icons.Default.VisibilityOff,
                        isChecked = state.incognitoMode,
                        onCheckedChange = { onToggleIncognito() }
                    )
                    ModFeatureToggleRow(
                        title = "Shutdown Confirmation",
                        subtitle = "Require device authentication to power off or restart",
                        icon = Icons.Default.PowerSettingsNew,
                        isChecked = state.shutdownConfirmation,
                        onCheckedChange = { onToggleShutdownConfirm() }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun MetricChip(
    icon: ImageVector,
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = label,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
        }
    }
}

@Composable
fun ActionBentoCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String
) {
    Surface(
        modifier = modifier
            .testTag(testTag)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun ModFeatureToggleRow(
    title: String,
    subtitle: String,
    icon: ImageVector,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MiuiTeal.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MiuiTeal,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.Black,
                checkedTrackColor = MiuiTeal
            )
        )
    }
}
