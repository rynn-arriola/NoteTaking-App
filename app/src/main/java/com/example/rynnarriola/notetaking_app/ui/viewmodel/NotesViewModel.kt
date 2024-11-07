package com.example.rynnarriola.notetaking_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rynnarriola.notetaking_app.data.local.entity.NoteEntity
import com.example.rynnarriola.notetaking_app.data.repository.NotesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val repository: NotesRepo) : ViewModel() {
    val notes: Flow<List<NoteEntity>> = repository.getAllNotes()

    fun getNoteById(id: Int): Flow<NoteEntity> = repository.getNoteById(id)

    fun addNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.insert(note)
        }
    }
    //additional functionality but not part of the feature
    fun removeNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }
    //additional functionality but not part of the feature
    fun editNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.update(note)
        }
    }
}