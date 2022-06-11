package com.example.notes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes.R
import com.example.notes.entities.Notes
import com.example.notes.interfaces.ViewModelable
import com.example.notes.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_notes.*
import java.time.LocalDateTime
import java.util.*


class AddNoteFragment : Fragment() {
    private lateinit var noteViewModel: NoteViewModel
    private val args: AddNoteFragmentArgs by navArgs()
    private var isUpdate = false
    private var currentNote: Notes? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as ViewModelable).getViewModel()

        done.setOnClickListener {
            if(isUpdate){
                update(currentNote!!)
                Toast.makeText(requireContext(), "Note updated", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_addNoteFragment_to_notesFragment)
            }
            else {
                addNote()
                Toast.makeText(requireContext(), "Note added", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addNoteFragment_to_notesFragment)
            }
        }
        isUpdating()

        delete.setOnClickListener {
            delete(currentNote!!)
        }
    }

    private fun addNote(){
        val title = titleNoteEdit.text.toString()
        val text = noteEditText.text.toString()

        val newNote = Notes()
        newNote.title = title
        newNote.noteText = text
        newNote.dateTime = LocalDateTime.now().toString()

        noteViewModel.insert(newNote)
    }

    private fun isUpdating(){
        currentNote = args.note

        if(currentNote != null){
            titleNoteEdit.setText(currentNote!!.title)
            noteEditText.setText(currentNote!!.noteText)
            isUpdate = true
        }
    }

    private fun update(note: Notes){
        note.title = titleNoteEdit.text.toString()
        note.noteText = noteEditText.text.toString()
        note.dateTime = LocalDateTime.now().toString()
        noteViewModel.insert(note)
    }


    private fun delete(note: Notes){
        noteViewModel.delete(note)
        Toast.makeText(requireContext(), "Note deleted", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_addNoteFragment_to_notesFragment)
    }
}