package com.example.todolistapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolistapp.R
import com.example.todolistapp.presentation.theme.ColorByTheme
import com.example.todolistapp.presentation.theme.fontInter
import com.example.todolistapp.presentation.theme.fontKanit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dialog(
    isOpen: Boolean,
    focusRequester: FocusRequester,
    placeholder: String,
    btnText: String,
    title: String,
    focusManager: FocusManager,
    onClose: () -> Unit,
    onClick: () -> Unit,
    darkTheme: Boolean,
    textInputState: State<String>,
    onValueChange: (String) -> Unit,
) {
    AnimatedVisibility(
        visible =isOpen,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        BasicAlertDialog(
            onDismissRequest = onClose
        ) {
            Column(
                modifier = Modifier
                    .height(180.dp)
                    .width(300.dp)
                    .background(
                        ColorByTheme(
                            lightScheme = colorResource(R.color.white),
                            darkScheme = colorResource(R.color.black),
                            darkTheme
                        )(), shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        1.dp, ColorByTheme(
                            lightScheme = colorResource(R.color.purple),
                            darkScheme = colorResource(R.color.white),
                            darkTheme = darkTheme
                        )(), RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 30.dp)
                    .padding(bottom = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    color = ColorByTheme(
                        lightScheme = colorResource(R.color.black),
                        darkScheme = colorResource(R.color.white),
                        darkTheme = darkTheme
                    )(),
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 18.dp, bottom = 10.dp)
                )
                TextField(
                    placeholder = placeholder,
                    darkTheme = darkTheme,
                    textInputState = textInputState,
                    onValueChange = onValueChange,
                    modifier = Modifier.focusRequester(focusRequester),
                    keyboardActions = KeyboardActions(onDone = {onClick()}),
                    focusRequester = focusRequester
                )

                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CustomButton(
                        onClick = { onClose()
                            focusManager.clearFocus(true)
                                  },
                        textColor = ColorByTheme(
                            lightScheme = colorResource(R.color.purple),
                            darkScheme = colorResource(R.color.white),
                            darkTheme = darkTheme
                        )(),
                        containerColor = ColorByTheme(
                            lightScheme = colorResource(R.color.white),
                            darkScheme = colorResource(R.color.black),
                            darkTheme = darkTheme
                        )(),
                        text = "CANCEL"
                    )

                    CustomButton(
                        onClick = onClick,
                        textColor = colorResource(R.color.white),
                        containerColor = colorResource(R.color.purple),
                        text = btnText
                    )
                }
            }
        }
    }

}


@Composable
fun CustomButton(
    onClick: () -> Unit,
    textColor: Color,
    containerColor: Color,
    text: String,

    ) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .width(100.dp)
            .height(40.dp),
        border = BorderStroke(1.dp, color = colorResource(R.color.purple)),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        )
    ) {
        Text(
            text = text,
            fontFamily = fontKanit,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}