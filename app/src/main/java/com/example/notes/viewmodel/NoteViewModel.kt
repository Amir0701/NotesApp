package com.example.notes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.notes.database.NotesDatabase
import com.example.notes.entities.Notes
import com.example.notes.repositories.NoteRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private var noteRepository: NoteRepository
    private val database: NotesDatabase = NotesDatabase.getDatabase(application)
    private var mutableList: MutableLiveData<List<Notes>> = MutableLiveData()

    init {
        noteRepository = NoteRepository(database)
    }

    fun getAllNotes(): LiveData<List<Notes>>{
        viewModelScope.launch {
            mutableList.postValue(noteRepository.getAllNotes());
        }
        return mutableList
    }

    fun insert(note: Notes){
        viewModelScope.launch {
            noteRepository.insertNote(note)
        }
    }


    fun delete(note: Notes){
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }

    fun getById(id: Int) = viewModelScope.launch {
        noteRepository.getNoteById(id)
    }

    fun getLiveData(): LiveData<List<Notes>>{
        return mutableList
    }

}