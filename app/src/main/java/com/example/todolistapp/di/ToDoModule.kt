package com.example.todolistapp.di

import android.content.Context
import androidx.room.Room
import com.example.todolistapp.data.local.ToDoDao
import com.example.todolistapp.data.local.ToDoDatabase
import com.example.todolistapp.data.repository.ToDoRepositoryImp
import com.example.todolistapp.domain.repository.ToDoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToDoModule {
    @Provides
    @Singleton
    fun provideToDoRepositoryImp(toDoDao: ToDoDao): ToDoRepository{
        return ToDoRepositoryImp(toDoDao)
    }

    @Provides
    @Singleton
    fun provideToDoDao(toDoDatabase: ToDoDatabase): ToDoDao {
        return toDoDatabase.dao
    }

    @Provides
    @Singleton
    fun provideToDoDatabase(@ApplicationContext context: Context): ToDoDatabase{
        return  Room.databaseBuilder(
            context = context,
            klass = ToDoDatabase::class.java,
            name = "todo.db"
        ).fallbackToDestructiveMigration().build()
    }
}