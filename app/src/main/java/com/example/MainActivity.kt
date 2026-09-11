package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.ui.SecurityApp
import com.example.ui.theme.MiuiSecurityTheme
import com.example.viewmodel.SecurityViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: SecurityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.initializeDeviceData(this)

        setContent {
            MiuiSecurityTheme(darkTheme = true) {
                SecurityApp(viewModel = viewModel)
            }
        }
    }
}
