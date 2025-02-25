package com.example.todolistapp.presentation.utils

enum class Options {
    ALL,
    COMPLETE,
    INCOMPLETE;

    companion object {
        fun toList() = Options.entries.toList()
    }
}
