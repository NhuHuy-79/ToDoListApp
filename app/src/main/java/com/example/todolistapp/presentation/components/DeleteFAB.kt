package com.example.todolistapp.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.todolistapp.R
import com.example.todolistapp.presentation.theme.ColorByTheme

@Composable
fun DeleteFAB(
    onRemove: () -> Unit,
    darkTheme: Boolean,
    modifier: Modifier = Modifier
) {
        FilledIconButton(
            onClick = onRemove,
            modifier = modifier.size(60.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = ColorByTheme(
                    darkScheme = colorResource(R.color.selected_delete_btn),
                    lightScheme = colorResource(R.color.purple),
                    darkTheme = darkTheme
                )(),
                contentColor = colorResource(R.color.white)
            ),
            shape = CircleShape,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_sweep),
                contentDescription = "Clear all ToDos",
                modifier = Modifier.size(26.dp)
            )
        }
}