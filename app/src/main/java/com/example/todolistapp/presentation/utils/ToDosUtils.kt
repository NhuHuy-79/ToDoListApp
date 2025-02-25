package com.example.todolistapp.presentation.utils

import com.example.todolistapp.data.model.ToDo


fun List<ToDo>.matchOptions(option: Options): List<ToDo>{
    return this.filter {toDo ->
        when (option){
            Options.ALL -> true
            Options.COMPLETE -> toDo.isDone
            Options.INCOMPLETE -> !toDo.isDone
        }
    }
}