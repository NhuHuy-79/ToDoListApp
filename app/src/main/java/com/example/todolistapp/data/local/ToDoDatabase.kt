package com.example.todolistapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todolistapp.data.model.ToDo

@Database(
    entities = [ToDo::class],
    version = 1,
    exportSchema = false
)
abstract class ToDoDatabase : RoomDatabase(){
    abstract val dao: ToDoDao
}