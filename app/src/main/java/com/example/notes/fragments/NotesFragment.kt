package com.example.notes.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.notes.R
import com.example.notes.adapters.NoteAdapter
import com.example.notes.entities.Notes
import com.example.notes.interfaces.ViewModelable
import com.example.notes.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_notes.*


class NotesFragment : Fragment() {
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter
    private  var notes: List<Notes> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_notes, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNoteButton.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("note", null)
            findNavController().navigate(R.id.action_notesFragment_to_addNoteFragment, bundle)
        })

        setUpRecyclerView()
        noteViewModel = (activity as ViewModelable).getViewModel()
        noteViewModel.getAllNotes().observe(viewLifecycleOwner, Observer {
            noteAdapter.differ.submitList(it)
            notes = it
        })

        noteAdapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener{
            override fun onItemClick(note: Notes, position: Int) {
                val bundle = Bundle()
                bundle.putSerializable("note", note)
                findNavController().navigate(R.id.action_notesFragment_to_addNoteFragment, bundle)
            }
        })

        search()
    }

    private fun setUpRecyclerView(){
        notesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        noteAdapter = NoteAdapter()
        notesRecyclerView.adapter = noteAdapter
    }

    private fun search(){
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                var searchList = ArrayList<Notes>()
                for(note in notes){
                    if(note.title!!.contains(p0.toString(), true) || note.noteText!!.contains(p0.toString(), true)){
                            searchList.add(note)
                    }
                }

                noteAdapter.differ.submitList(searchList)
                return true
            }

        })
    }
}