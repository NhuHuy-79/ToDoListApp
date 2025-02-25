package com.example.todolistapp.domain.repository

import com.example.todolistapp.data.model.ToDo
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    fun getAllToDos(): Flow<List<ToDo>>

    suspend fun upsertToDo(toDo: ToDo)

    suspend fun toggleComplete(id: Int)

    suspend fun deleteToDo(id: Int)

    suspend fun removeToDo(id: Int)

    suspend fun undoToDo(id: Int)

    suspend fun removeAll()
}