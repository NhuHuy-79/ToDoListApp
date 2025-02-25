package com.example.todolistapp.presentation.events

sealed class OnClickEvent {
    data class OnApply(val value: String) : OnClickEvent()
    data class OnEdit(val id: Int, val value: String, val isDone: Boolean): OnClickEvent()
    data class OnDelete(val id: Int) : OnClickEvent()
    data class OnComplete(val id: Int) : OnClickEvent()
    data object OnUndo : OnClickEvent()
    data object OnRemove: OnClickEvent()
}


