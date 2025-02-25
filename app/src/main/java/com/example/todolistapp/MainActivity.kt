package com.example.todolistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import com.example.todolistapp.presentation.HomeScreen
import com.example.todolistapp.presentation.theme.ToDoListAppTheme
import com.example.todolistapp.presentation.viewmodels.HomeViewModel
import com.example.todolistapp.presentation.viewmodels.ToDoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val toDoViewModel: ToDoViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val focusRequester = remember { FocusRequester() }
            val focusManager = LocalFocusManager.current
            ToDoListAppTheme {
                HomeScreen(
                    toDoViewModel = toDoViewModel,
                    homeViewModel = homeViewModel,
                    onEvent = toDoViewModel::onEvent,
                    focusManager = focusManager,
                    focusRequester = focusRequester
                )
            }
        }
    }
}
