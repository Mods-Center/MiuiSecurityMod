package com.example.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.BatteryChargingFull
import androidx.compose.material.icons.outlined.CleaningServices
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.screens.BatteryScreen
import com.example.ui.screens.CleanerScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.SecurityScanScreen
import com.example.ui.screens.ToolboxModScreen
import com.example.ui.theme.MiuiCyan
import com.example.ui.theme.MiuiTeal
import com.example.viewmodel.SecurityViewModel

@Composable
fun SecurityApp(
    viewModel: SecurityViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .testTag("bottom_nav_bar"),
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp
            ) {
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 0) Icons.Filled.Security else Icons.Outlined.Security,
                            contentDescription = "Security",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = { Text("Security", fontSize = 11.sp) },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        selectedTextColor = MiuiTeal,
                        indicatorColor = MiuiTeal,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_tab_home")
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 1) Icons.Filled.CleaningServices else Icons.Outlined.CleaningServices,
                            contentDescription = "Cleaner",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = { Text("Cleaner", fontSize = 11.sp) },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        selectedTextColor = MiuiTeal,
                        indicatorColor = MiuiTeal,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_tab_cleaner")
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 2) Icons.Filled.BatteryChargingFull else Icons.Outlined.BatteryChargingFull,
                            contentDescription = "Battery",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = { Text("Battery", fontSize = 11.sp) },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        selectedTextColor = MiuiTeal,
                        indicatorColor = MiuiTeal,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_tab_battery")
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 3) Icons.Filled.Shield else Icons.Outlined.Shield,
                            contentDescription = "Scan",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = { Text("Scan", fontSize = 11.sp) },
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        selectedTextColor = MiuiTeal,
                        indicatorColor = MiuiTeal,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_tab_scan")
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 4) Icons.Filled.Tune else Icons.Outlined.Tune,
                            contentDescription = "Mod Tools",
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = { Text("Tools", fontSize = 11.sp) },
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        selectedTextColor = MiuiTeal,
                        indicatorColor = MiuiTeal,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_tab_tools")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> HomeScreen(
                    state = state,
                    onOptimizeClick = { viewModel.optimizeSystem() },
                    onNavigateToCleaner = { selectedTab = 1 },
                    onNavigateToBattery = { selectedTab = 2 },
                    onNavigateToSecurity = { selectedTab = 3 },
                    onNavigateToTools = { selectedTab = 4 },
                    onToggleIncognito = { viewModel.toggleIncognitoMode() },
                    onToggleShutdownConfirm = { viewModel.toggleShutdownConfirmation() }
                )
                1 -> CleanerScreen(
                    state = state,
                    onToggleCategory = { viewModel.toggleJunkSelection(it) },
                    onCleanClick = { viewModel.cleanSelectedJunk() },
                    onScanClick = { viewModel.startJunkScan() }
                )
                2 -> BatteryScreen(
                    state = state,
                    onSelectMode = { viewModel.setBatteryMode(it) },
                    onOptimizeApp = { viewModel.optimizeAppBattery(it) }
                )
                3 -> SecurityScanScreen(
                    state = state,
                    onStartScan = { viewModel.runSecurityScan() }
                )
                4 -> ToolboxModScreen(
                    state = state,
                    onToggleForce100 = { viewModel.toggleForce100Score() },
                    onToggleShutdownConfirm = { viewModel.toggleShutdownConfirmation() },
                    onToggleIncognito = { viewModel.toggleIncognitoMode() },
                    onToggleVideoToolbox = { viewModel.toggleVideoToolbox() },
                    onToggleGameTurbo = { viewModel.toggleGameTurbo() },
                    onUpdateGameFps = { id, fps -> viewModel.updateGameFps(id, fps) }
                )
            }
        }
    }
}
