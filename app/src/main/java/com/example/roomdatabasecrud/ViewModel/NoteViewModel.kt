package com.example.roomdatabasecrud.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabasecrud.Dao.Note
import com.example.roomdatabasecrud.Repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository): ViewModel() {
    private val _notes = mutableStateOf<List<Note>>(emptyList())
    val notes: State<List<Note>> get() = _notes

    init {
        loadNotes()
    }

    fun insert(note: Note){
        viewModelScope.launch {
            repository.insert(note)
            loadNotes()
        }
    }

    fun update(note: Note){
        viewModelScope.launch {
            repository.update(note)
            loadNotes()
        }
    }

    fun delete(note: Note){
        viewModelScope.launch {
            repository.delete(note)
            loadNotes()
        }
    }

    private fun loadNotes() {
        viewModelScope.launch {
            _notes.value = repository.getAllNotes()
        }
    }

}