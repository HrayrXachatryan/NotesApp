package com.example.notesapp.viewModel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope


import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.entity.Notes

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao = NoteDatabase.getDatabase(application).noteDao()

    private val _texts = mutableStateListOf<String>()
    val texts: SnapshotStateList<String> = _texts

    private val _dialogState = mutableStateOf(false)
    val dialogState: MutableState<Boolean> = _dialogState

    private val _editIndex = mutableStateOf<Int?>(null)
    val editIndex: MutableState<Int?> = _editIndex

    private val _isDarkTheme = mutableStateOf(false)
    val isDarkTheme: State<Boolean> = _isDarkTheme

    init {
        viewModelScope.launch {
            val allNotes = withContext(Dispatchers.IO) { noteDao.getAll() }
            _texts.addAll(allNotes.map { it.text })
        }
    }

    fun addItem(item: String) {
        _texts.add(item)
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.insert(Notes(text = item))
        }
    }

    fun removeItem(item: String) {
        _texts.remove(item)
        viewModelScope.launch(Dispatchers.IO) {
            val noteToDelete = noteDao.getAll().firstOrNull { it.text == item }
            noteToDelete?.let { noteDao.delete(it) }
        }
    }

    fun updateItem(index: Int, newText: String) {
        val oldText = _texts[index]
        _texts[index] = newText
        viewModelScope.launch(Dispatchers.IO) {
            val noteToUpdate = noteDao.getAll().firstOrNull { it.text == oldText }
            noteToUpdate?.let { noteDao.update(it.copy(text = newText)) }
        }
    }

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }

    fun setDarkTheme(enabled: Boolean) {
        _isDarkTheme.value = enabled
    }
}
