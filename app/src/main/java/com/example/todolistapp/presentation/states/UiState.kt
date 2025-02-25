package com.example.todolistapp.presentation.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class UiState(
    var isDarkMode: MutableState<Boolean> = mutableStateOf(false),
    var isDialogOpened: MutableState<Boolean> = mutableStateOf(false),
    var isEdit: MutableState<Pair<Int?,Boolean>> = mutableStateOf(Pair(null,false))

)