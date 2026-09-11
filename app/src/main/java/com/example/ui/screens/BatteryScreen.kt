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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryAlert
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppBatteryUsage
import com.example.model.BatteryMode
import com.example.ui.theme.MiuiCyan
import com.example.ui.theme.MiuiOrange
import com.example.ui.theme.MiuiRed
import com.example.ui.theme.MiuiTeal
import com.example.viewmodel.SecurityUiState

@Composable
fun BatteryScreen(
    state: SecurityUiState,
    onSelectMode: (BatteryMode) -> Unit,
    onOptimizeApp: (String) -> Unit,
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
            // MIUI Modded Battery Header Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("battery_hero_card"),
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
                                    Color(0xFF0C2B1D),
                                    Color(0xFF0F1B24)
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
                                text = "Battery & Performance",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "MIUI Mod Battery Engine",
                                fontSize = 11.sp,
                                color = MiuiTeal,
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
                                text = "${state.batteryLevel}%",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MiuiTeal
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "${state.remainingHours} hrs ${state.remainingMinutes} mins",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = if (state.isCharging) "Charging (${state.fastChargeType})" else "Remaining battery estimated",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // 4-Way Mode Selector Pills (Save Battery, Balanced, Performance, Ultra Saver)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF162338))
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ModePill(
                            title = "Save",
                            isSelected = state.batteryMode == BatteryMode.BATTERY_SAVER,
                            onClick = { onSelectMode(BatteryMode.BATTERY_SAVER) },
                            activeColor = MiuiTeal,
                            modifier = Modifier.weight(1f)
                        )
                        ModePill(
                            title = "Balanced",
                            isSelected = state.batteryMode == BatteryMode.BALANCED,
                            onClick = { onSelectMode(BatteryMode.BALANCED) },
                            activeColor = MiuiCyan,
                            modifier = Modifier.weight(1f)
                        )
                        ModePill(
                            title = "Performance",
                            isSelected = state.batteryMode == BatteryMode.PERFORMANCE,
                            onClick = { onSelectMode(BatteryMode.PERFORMANCE) },
                            activeColor = MiuiOrange,
                            modifier = Modifier.weight(1f)
                        )
                        ModePill(
                            title = "Ultra",
                            isSelected = state.batteryMode == BatteryMode.ULTRA_SAVER,
                            onClick = { onSelectMode(BatteryMode.ULTRA_SAVER) },
                            activeColor = Color(0xFF10B981),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // Hardware Diagnostics Cards (Temperature, Cycles, Health, Fast Charge)
        item {
            Text(
                text = "Hardware Battery Stats",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DiagnosticCard(
                    title = "Temperature",
                    value = "${state.batteryTemperature} °C",
                    sub = if (state.batteryTemperature < 35.0) "Cool" else "Warm",
                    icon = Icons.Default.Thermostat,
                    tint = if (state.batteryTemperature < 35.0) MiuiTeal else MiuiOrange,
                    modifier = Modifier.weight(1f)
                )
                DiagnosticCard(
                    title = "Battery Health",
                    value = "98%",
                    sub = state.batteryHealthCondition,
                    icon = Icons.Default.HealthAndSafety,
                    tint = MiuiCyan,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DiagnosticCard(
                    title = "Charge Cycles",
                    value = "${state.chargeCycleCount}",
                    sub = "Total full cycles",
                    icon = Icons.Default.BatteryChargingFull,
                    tint = MiuiTeal,
                    modifier = Modifier.weight(1f)
                )
                DiagnosticCard(
                    title = "Fast Charge",
                    value = "${state.batteryVoltageMv} mV",
                    sub = state.fastChargeType,
                    icon = Icons.Default.ElectricBolt,
                    tint = MiuiOrange,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // App Battery Usage Ranking
        item {
            Text(
                text = "Battery Drain Breakdown",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        items(state.appBatteryList) { app ->
            AppBatteryItem(
                app = app,
                onOptimize = { onOptimizeApp(app.packageName) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ModePill(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    activeColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) activeColor else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun DiagnosticCard(
    title: String,
    value: String,
    sub: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(tint.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = tint,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = sub,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
        }
    }
}

@Composable
fun AppBatteryItem(
    app: AppBatteryUsage,
    onOptimize: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = app.appName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${String.format("%.1f", app.usagePercentage)}%",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MiuiCyan
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { (app.usagePercentage / 25f).coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = MiuiTeal,
                    trackColor = Color(0xFF1E293B)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${app.drainMah} mAh consumed",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            if (app.isOptimized) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(MiuiTeal.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "SAVED",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MiuiTeal
                    )
                }
            } else {
                Button(
                    onClick = onOptimize,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MiuiTeal,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text(text = "Save", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
