package com.example.todolistapp.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class ColorByTheme(
    val lightScheme: Color,
    val darkScheme: Color,
    val darkTheme: Boolean
) {
    constructor(
        color: Color
    ) : this(
        lightScheme = color,
        darkScheme = color,
        darkTheme = false
    )

    @Composable
    operator fun invoke(): Color = if (darkTheme) darkScheme else lightScheme
}