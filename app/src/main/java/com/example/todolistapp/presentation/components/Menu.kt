package com.example.todolistapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolistapp.R
import com.example.todolistapp.presentation.utils.Options
import com.example.todolistapp.presentation.theme.ColorByTheme
import com.example.todolistapp.presentation.theme.fontKanit

@Composable
fun Menu(
    modifier: Modifier = Modifier,
    darkTheme: Boolean,
    options: List<Options>,
    currOption: Options,
    onClick: (Options) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(durationMillis = 400)
    )
    Column(
        modifier = modifier
            .padding(bottom = 16.dp)
    ) {
        Button(
            modifier = Modifier
                .height(40.dp)
                .width(150.dp),
            onClick = { expanded = !expanded },
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ColorByTheme(
                    lightScheme = colorResource(R.color.black),
                    darkTheme = darkTheme,
                    darkScheme = colorResource(R.color.purple)
                )()
            )
        ) {
            Text(
                text = currOption.name,
                fontSize = 12.sp,
                fontFamily = fontKanit,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_arrow),
                contentDescription = null,
                modifier = Modifier.rotate(rotation)
            )
        }
        AnimatedVisibility(
            visible = expanded,
            modifier = Modifier
                .padding(top = 4.dp)
                .clip(RoundedCornerShape(6.dp))
        ) {
            Column(
                modifier = Modifier
                    .width(150.dp)
                    .height(105.dp)
                    .animateEnterExit(
                        enter = fadeIn(
                            initialAlpha = 0.3f
                        ), exit = fadeOut(
                            animationSpec = tween(durationMillis = 500)
                        )
                    )
                    .background(colorResource(R.color.white))
                    .border(
                        2.dp, color = ColorByTheme(
                            lightScheme = colorResource(R.color.black),
                            darkTheme = darkTheme,
                            darkScheme = colorResource(R.color.purple)
                        )(), RoundedCornerShape(6.dp)
                    )
            ) {
                options.forEach { option ->
                    MenuItem(
                        onClick = {
                            onClick(option)
                            expanded = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp),
                        option = option,
                        darkTheme = darkTheme,
                        enabled = option != currOption
                    )
                }

            }
        }

    }
}

@Composable
fun MenuItem(
    onClick: (Options) -> Unit,
    modifier: Modifier,
    option: Options,
    darkTheme: Boolean,
    enabled: Boolean
) {
    Button(
        onClick = { onClick(option) },
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.white)
        ),
        shape = RectangleShape
    ) {
        Text(
            text = option.name,
            fontFamily = fontKanit,
            fontSize = 14.sp,
            color = ColorByTheme(
                lightScheme = colorResource(R.color.black),
                darkScheme = colorResource(R.color.purple),
                darkTheme = darkTheme
            )(),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
