package com.example.notes.interfaces

import androidx.lifecycle.AndroidViewModel
import com.example.notes.viewmodel.NoteViewModel

interface ViewModelable {
    fun getViewModel(): NoteViewModel
}