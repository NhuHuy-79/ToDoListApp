package com.example.todolistapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolistapp.R
import com.example.todolistapp.presentation.theme.ColorByTheme

@Composable
fun TextField(
    placeholder: String,
    darkTheme: Boolean,
    modifier: Modifier,
    focusRequester: FocusRequester,
    textInputState: State<String>,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions
) {
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    BasicTextField(
        value = textInputState.value,
        singleLine = true,
        onValueChange = onValueChange,
        keyboardActions = keyboardActions,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .height(40.dp)
            .border(
                1.dp, ColorByTheme(
                    lightScheme = colorResource(R.color.black),
                    darkScheme = colorResource(R.color.white),
                    darkTheme = darkTheme
                )(), RoundedCornerShape(6.dp)
            )
            .background(
                color = ColorByTheme(
                    lightScheme = colorResource(R.color.white),
                    darkScheme = colorResource(R.color.black),
                    darkTheme = darkTheme
                )()
            ),
        textStyle = TextStyle(
            fontSize = 12.sp,
            color = ColorByTheme(
                lightScheme = colorResource(R.color.text_field_font),
                darkScheme = colorResource(R.color.text_field_font_dark),
                darkTheme = darkTheme
            )()
        ),
        decorationBox = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = textInputState.value.ifBlank { placeholder },
                    fontSize = 14.sp,
                    color = ColorByTheme(
                        lightScheme = colorResource(R.color.black),
                        darkScheme = colorResource(R.color.white),
                        darkTheme = darkTheme
                    )()
                )
            }

        }
    )
}