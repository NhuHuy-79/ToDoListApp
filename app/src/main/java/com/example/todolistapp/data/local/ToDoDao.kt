package com.example.todolistapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.todolistapp.data.model.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM TODO WHERE ISDELETED = FALSE")
    fun getAllToDos(): Flow<List<ToDo>>

    @Query("SELECT * FROM TODO WHERE ISDONE = 1 AND ISDELETED = 0")
    fun getCompletedToDos(): Flow<List<ToDo>>

    @Query("SELECT * FROM TODO WHERE ISDONE = 0 AND ISDELETED = 0")
    fun getUncompletedToDo(): Flow<List<ToDo>>

    @Upsert
    suspend fun upsertToDo(toDo: ToDo)

    @Query("UPDATE todo SET isDone = NOT isDone WHERE id =:id")
    suspend fun toggleComplete(id: Int)

    @Query("UPDATE todo SET isDeleted = 1 WHERE id =:id")
    suspend fun deleteToDo(id: Int)

    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun removeToDo(id: Int)

    @Query("UPDATE todo SET isDeleted = 0 WHERE id =:id")
    suspend fun undoToDo(id: Int)

    @Query("DELETE FROM todo")
    suspend fun removeAll()
}