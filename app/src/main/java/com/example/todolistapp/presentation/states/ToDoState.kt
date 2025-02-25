package com.example.todolistapp.presentation.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.todolistapp.data.model.ToDo

data class ToDoState(
    val toDos: List<ToDo> = emptyList(),
    val content: MutableState<String> = mutableStateOf(""),
    val id: MutableState<Int> = mutableIntStateOf(0),
    val isDone: MutableState<Boolean> = mutableStateOf(false)
)
