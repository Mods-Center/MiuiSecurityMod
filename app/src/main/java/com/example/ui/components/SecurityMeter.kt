package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SecurityScoreStatus
import com.example.ui.theme.MiuiCyan
import com.example.ui.theme.MiuiOrange
import com.example.ui.theme.MiuiRed
import com.example.ui.theme.MiuiTeal

@Composable
fun SecurityMeter(
    score: Int,
    status: SecurityScoreStatus,
    isOptimizing: Boolean,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "meter_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )
    val sweepAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radar_sweep"
    )

    val primaryMeterColor = when (status) {
        SecurityScoreStatus.SECURE -> MiuiTeal
        SecurityScoreStatus.OPTIMIZING -> MiuiCyan
        SecurityScoreStatus.SCANNING -> MiuiCyan
        SecurityScoreStatus.ATTENTION -> MiuiOrange
    }

    Box(
        modifier = modifier.size(190.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer glowing halo ring
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 10.dp.toPx()
            val radius = (size.minDimension - strokeWidth) / 2

            // Background circle track
            drawCircle(
                color = Color(0xFF1E293B).copy(alpha = 0.7f),
                radius = radius,
                style = Stroke(width = strokeWidth)
            )

            // Animated progress arc
            val sweep = (score / 100f) * 360f
            drawArc(
                brush = Brush.sweepGradient(
                    listOf(
                        primaryMeterColor.copy(alpha = 0.5f),
                        primaryMeterColor,
                        MiuiCyan
                    )
                ),
                startAngle = if (isOptimizing) sweepAngle else -90f,
                sweepAngle = if (isOptimizing) 270f else sweep,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            if (isOptimizing) {
                drawCircle(
                    color = primaryMeterColor.copy(alpha = pulseAlpha * 0.2f),
                    radius = radius * 0.9f
                )
            }
        }

        // Center score display
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isOptimizing) "..." else "$score",
                fontSize = 44.sp,
                fontWeight = FontWeight.ExtraBold,
                color = primaryMeterColor,
                lineHeight = 46.sp
            )
            Text(
                text = if (isOptimizing) "Optimizing" else "Score",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(primaryMeterColor.copy(alpha = 0.18f))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = if (score == 100) "PROTECTED" else "SCAN ACTIVE",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryMeterColor,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}
