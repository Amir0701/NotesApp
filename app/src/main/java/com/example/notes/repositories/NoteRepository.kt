package com.example.notes.repositories

import androidx.lifecycle.MutableLiveData
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.notes.dao.NotesDao
import com.example.notes.database.NotesDatabase
import com.example.notes.entities.Notes

class NoteRepository(notesDatabase: NotesDatabase) {
    private var notesDao: NotesDao
    init {
        notesDao = notesDatabase.getDao()
    }
    suspend fun getAllNotes(): List<Notes>{
        return notesDao.getAllNotes();
    }

    suspend fun insertNote(note: Notes){
        notesDao.insert(note)
    }

    suspend fun deleteNote(note: Notes){
        notesDao.delete(note)
    }

    suspend fun getNoteById(id: Int) = notesDao.getNoteById(id)

    suspend fun getNoteByString(searchString: SupportSQLiteQuery): List<Notes>{
        return notesDao.getNotesByString(searchString)
    }
}