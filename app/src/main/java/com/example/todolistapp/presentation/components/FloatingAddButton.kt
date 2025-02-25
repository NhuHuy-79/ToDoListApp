package com.example.todolistapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.todolistapp.R
import com.example.todolistapp.presentation.theme.ColorByTheme

@Composable
fun ToDoFAB(
    onAddToDo: () -> Unit,
    darkTheme: Boolean,
    onRemove: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DeleteFAB(
            onRemove = onRemove,
            darkTheme = darkTheme,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(16.dp))
        FloatingAddButton(
            onAddToDo = onAddToDo,
            darkTheme = darkTheme,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun FloatingAddButton(
    onAddToDo: () -> Unit,
    darkTheme: Boolean,
    modifier: Modifier
) {
    ElevatedButton(
        modifier = modifier.size(80.dp),
        border = BorderStroke(
            2.dp, color = ColorByTheme(
                lightScheme = colorResource(R.color.black),
                darkScheme = colorResource(R.color.purple),
                darkTheme = darkTheme
            )()
        ),
        shape = CircleShape,
        onClick = onAddToDo,
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorByTheme(
                lightScheme = colorResource(R.color.black),
                darkScheme = colorResource(R.color.purple),
                darkTheme = darkTheme
            )(),
            contentColor = ColorByTheme(
                lightScheme = LocalContentColor.current,
                darkScheme = colorResource(R.color.white),
                darkTheme = darkTheme
            )()
        )
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.icon_add),
            contentDescription = "Add todo",
            modifier = Modifier.size(32.dp),
            tint = colorResource(R.color.white)
        )
    }
}