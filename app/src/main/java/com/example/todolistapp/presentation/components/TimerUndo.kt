package com.example.todolistapp.presentation.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolistapp.R
import com.example.todolistapp.presentation.theme.ColorByTheme
import com.example.todolistapp.presentation.theme.fontRaleway


@Composable
fun TimerUndo(
    isDisplayed: Boolean,
    darkTheme: Boolean,
    onUndo: () -> Unit,
    modifier: Modifier
) {
    AnimatedVisibility(
        visible = isDisplayed,
        modifier = modifier
    ) {
        Card(
            onClick = onUndo,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .animateEnterExit(
                    enter = fadeIn() + slideInVertically()
                )
                .animateEnterExit(
                    exit = fadeOut() + slideOutVertically()

                )
                .height(50.dp)
                .width(150.dp),
            shape = RoundedCornerShape(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = ColorByTheme(
                    lightScheme = colorResource(R.color.black),
                    darkScheme = colorResource(R.color.purple),
                    darkTheme = darkTheme
                )(),
            ),
            border = BorderStroke(2.dp, color = colorResource(R.color.purple))
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxSize()
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DisappearingCircle(number = 5)
                Spacer(modifier = Modifier.padding(end = 4.dp))
                Text(
                    text = "UNDO",
                    color = colorResource(R.color.white),
                    fontSize = 16.sp,
                    fontFamily = fontRaleway,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.padding(end = 4.dp))
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_undo),
                    contentDescription = "Undo",
                    modifier = Modifier.size(16.dp),
                    tint = colorResource(R.color.white)
                )
            }
        }
    }

}

@Composable
fun DisappearingCircle(
    number: Int,
    radius: Dp = 16.dp,
    color: Color = Color.White,
    strokeWidth: Dp = 2.dp,
    animationDuration: Int = 5000,
) {
    var animationPlayed by remember { mutableStateOf(true) }
    val currentPercentage by animateFloatAsState(
        targetValue = if (animationPlayed) number.toFloat() else 0f,
        animationSpec = tween(durationMillis = animationDuration, easing = LinearEasing)
    )
    LaunchedEffect(Unit) {
        animationPlayed = false
    }
    Box(modifier = Modifier.size(radius * 2.5f), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(radius * 2)) {
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = -360f * currentPercentage / number,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
                useCenter = false
            )
        }
        Text(
            text = currentPercentage.toInt().toString(),
            color = colorResource(R.color.white),
            fontSize = 14.sp,
            fontFamily = fontRaleway,
            fontWeight = FontWeight.Bold
        )
    }
}
