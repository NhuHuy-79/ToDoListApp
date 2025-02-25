package com.example.todolistapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "todo")
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val content: String,
    val isDone: Boolean = false,
    val isDeleted: Boolean = false
)
