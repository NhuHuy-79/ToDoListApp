package com.example.todolistapp.presentation.viewmodels


import androidx.lifecycle.ViewModel
import com.example.todolistapp.presentation.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(UiState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    fun toggleDarkMode() {
      _uiStateFlow.value.isDarkMode.value = !_uiStateFlow.value.isDarkMode.value
    }

    fun openEditTxtField(id: Int) {
       _uiStateFlow.value.isEdit.value = Pair(id,true)
    }
    fun onExitTxtField(id: Int):Boolean{
        return _uiStateFlow.value.isEdit.value == Pair(id,true)
    }
    fun closeEditTxtField() {
        _uiStateFlow.value.isEdit.value = Pair(null,false)
    }

    fun openDialog() {
        _uiStateFlow.value.isDialogOpened.value = true
    }

    fun cancelDialog() {
        _uiStateFlow.value.isDialogOpened.value = !_uiStateFlow.value.isDialogOpened.value
    }
}