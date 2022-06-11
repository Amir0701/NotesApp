package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import com.example.notes.interfaces.ViewModelable
import com.example.notes.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity(), ViewModelable {
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        setContentView(R.layout.activity_main)

    }

    override fun getViewModel(): NoteViewModel {
        return noteViewModel
    }

}