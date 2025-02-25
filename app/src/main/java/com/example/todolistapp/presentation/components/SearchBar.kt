package com.example.todolistapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolistapp.R
import com.example.todolistapp.presentation.theme.ColorByTheme
import com.example.todolistapp.presentation.theme.fontInter


@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    darkTheme: Boolean
) {
    BasicTextField(
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        modifier = Modifier
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
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        decorationBox = { _ ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = value,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = ColorByTheme(
                        lightScheme = colorResource(R.color.black),
                        darkScheme = colorResource(R.color.white),
                        darkTheme = darkTheme
                    )()
                )
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_search),
                    contentDescription = null,
                    tint = ColorByTheme(
                        lightScheme = colorResource(R.color.black),
                        darkScheme = colorResource(R.color.white),
                        darkTheme = darkTheme
                    )()

                )
            }
        }
    )
}