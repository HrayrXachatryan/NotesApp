package com.example.notesapp.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel

class ScreenViewModel: ViewModel() {

    private val _texts = mutableStateListOf<String>("Первый длинный текст для демонстрации...",
        "Второй длинный текст, который тоже можно развернуть.",
        "Третий текст с возможностью разворачивания.")
    val texts: SnapshotStateList<String> = _texts

    fun addItem(item: String) {
        _texts.add(item)
    }

    fun removeItem(item: String) {
        _texts.remove(item)
    }

    private val _dialogState = mutableStateOf<Boolean>(false)
    val dialogState : MutableState<Boolean> = _dialogState

    private val _editIndex = mutableStateOf<Int?>(null)
    val editIndex : MutableState<Int?> = _editIndex
}