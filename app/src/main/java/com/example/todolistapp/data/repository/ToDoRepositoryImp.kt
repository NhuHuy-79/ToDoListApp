package com.example.todolistapp.data.repository

import com.example.todolistapp.data.local.ToDoDao
import com.example.todolistapp.data.model.ToDo
import com.example.todolistapp.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToDoRepositoryImp @Inject constructor(private val toDoDao: ToDoDao) : ToDoRepository{

    override fun getAllToDos(): Flow<List<ToDo>> = toDoDao.getAllToDos()

    override suspend fun upsertToDo(toDo: ToDo) = toDoDao.upsertToDo(toDo)

    override suspend fun toggleComplete(id: Int) = toDoDao.toggleComplete(id)

    override suspend fun deleteToDo(id: Int)  = toDoDao.deleteToDo(id)

    override suspend fun removeToDo(id: Int) = toDoDao.removeToDo(id)

    override suspend fun undoToDo(id: Int) = toDoDao.undoToDo(id)

    override suspend fun removeAll() = toDoDao.removeAll()
}