package com.example.todolistapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolistapp.R
import com.example.todolistapp.presentation.theme.ColorByTheme
import com.example.todolistapp.presentation.theme.fontKanit


@Composable
fun EditTextField(
    placeholder: String,
    value: String,
    focusRequester: FocusRequester,
    darkTheme: Boolean,
    onValueChange: (String)->Unit,
    keyboardActions: KeyboardActions,
    onCancel: ()-> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    BasicTextField(
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .height(50.dp)
            .background(
                color = ColorByTheme(
                    lightScheme = colorResource(R.color.white),
                    darkScheme = colorResource(R.color.black),
                    darkTheme = darkTheme
                )()
            ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = keyboardActions,
        decorationBox = { _ ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, color = ColorByTheme(
                        lightScheme = colorResource(R.color.black),
                        darkScheme = colorResource(R.color.purple),
                        darkTheme = darkTheme

                    )()),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = value.ifBlank { placeholder },
                    fontFamily = fontKanit,
                    modifier = Modifier.padding(start = 8.dp),
                    fontSize = 20.sp,
                    color = if (value.isBlank()) colorResource(R.color.text_field_font_dark) else ColorByTheme(
                        lightScheme = colorResource(R.color.black),
                        darkScheme = colorResource(R.color.white),
                        darkTheme = darkTheme
                    )()
                )
               IconButton(onClick = onCancel) {
                   Icon(
                       imageVector = Icons.Filled.Close, contentDescription = null, tint = ColorByTheme(
                           darkTheme = darkTheme,
                           lightScheme = colorResource(R.color.black),
                           darkScheme = colorResource(R.color.white),
                       )(),
                       modifier = Modifier.size(20.dp)
                   )
               }
            }
        }
    )
}