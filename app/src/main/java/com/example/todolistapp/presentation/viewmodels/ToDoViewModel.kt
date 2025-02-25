package com.example.todolistapp.presentation.viewmodels


import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.data.model.ToDo
import com.example.todolistapp.domain.repository.ToDoRepository
import com.example.todolistapp.presentation.events.OnClickEvent
import com.example.todolistapp.presentation.utils.Options
import com.example.todolistapp.presentation.states.ToDoState
import com.example.todolistapp.presentation.utils.matchOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(private val toDoRepository: ToDoRepository) : ViewModel() {

    private var _toDoStateFlow = MutableStateFlow(ToDoState())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _options = MutableStateFlow(Options.COMPLETE)
    val option = _options.asStateFlow()

    private val toDoFlow = toDoRepository.getAllToDos()

    private var deletedToDoId: Int = 0

    val undoState = mutableStateOf(false)
    private var undoCoroutine: Job? = null


    val toDos = combine(
        toDoFlow,
        _toDoStateFlow,
        searchQuery,
        option
    ) { toDos, toDoState, searchQuery, option ->
        toDoState.copy(
            toDos = if (searchQuery.isBlank()) toDos.matchOptions(option) else toDos.matchOptions(
                option
            ).filter { toDo ->
                toDo.content.contains(searchQuery, ignoreCase = true)
            }
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), ToDoState()
    )

    fun onEvent(event: OnClickEvent) {
        when (event) {
            is OnClickEvent.OnApply -> {
                viewModelScope.launch {
                    if (event.value.isNotEmpty()) {
                        val newTodo = ToDo(content = event.value)
                        toDoRepository.upsertToDo(newTodo)
                        resetTextField()
                    }
                }
            }

            is OnClickEvent.OnDelete -> {
                viewModelScope.launch {
                    toDoRepository.deleteToDo(id = event.id)
                }
                saveDeleteId(event.id)
                openUndo()
                undoCoroutine = viewModelScope.launch {
                    delay(5000)
                    if (undoState.value) {
                        removeToDo()
                        closeUndo()
                    }
                }
            }

            is OnClickEvent.OnComplete -> {
                viewModelScope.launch {
                    toDoRepository.toggleComplete(event.id)
                }
            }

            is OnClickEvent.OnUndo -> {
                viewModelScope.launch {
                    toDoRepository.undoToDo(deletedToDoId)
                }
                closeUndo()
                undoCoroutine?.cancel()
            }

            is OnClickEvent.OnEdit -> {
                val toDo = ToDo(id = event.id, content = event.value, isDone = event.isDone)
                viewModelScope.launch {
                    toDoRepository.upsertToDo(toDo)
                    resetTextField()
                }
            }

            OnClickEvent.OnRemove -> {
                viewModelScope.launch {
                    toDoRepository.removeAll()
                }
            }
        }
    }
    fun updateToDo(id: Int, isDone: Boolean){
        _toDoStateFlow.value.id.value = id
        _toDoStateFlow.value.isDone.value = isDone
    }
    fun updateQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateOption(option: Options) {
        _options.value = option
    }

    private fun resetTextField() {
        toDos.value.content.value = ""
    }

    private fun saveDeleteId(id: Int) {
        deletedToDoId = id
    }

    private fun closeUndo() {
        undoState.value = false
    }

    private fun openUndo() {
        undoState.value = true
        undoCoroutine
    }

    private fun removeToDo() {
        viewModelScope.launch {
            toDoRepository.removeToDo(deletedToDoId)
        }
    }

}