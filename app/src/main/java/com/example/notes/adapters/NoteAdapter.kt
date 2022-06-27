package com.example.notes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.entities.Notes
import kotlinx.android.synthetic.main.note_card.view.*

class NoteAdapter(): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){
    private lateinit var onItemClickListener: OnItemClickListener

    private var diffNote: ItemCallback<Notes> = object : ItemCallback<Notes>(){
        override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            var flag = false
            if(oldItem.title.equals(newItem.title)){
                if(oldItem.noteText.equals(newItem.noteText)){
                    if(oldItem.dateTime.equals(newItem.dateTime)){
                        flag = true
                    }
                }
            }
            return flag
        }

    }

    val differ = AsyncListDiffer(this, diffNote)

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var noteCard: CardView = itemView.findViewById(R.id.note_card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.note_card, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val titleTextView: TextView = holder.noteCard.findViewById(R.id.title_note_card)
        val textView: TextView = holder.noteCard.findViewById(R.id.text_note_card)

        val currentNote: Notes = differ.currentList.get(position)
        titleTextView.text = currentNote.title
        textView.text = currentNote.noteText

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(currentNote, position)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnItemClickListener{
        fun onItemClick(note: Notes, position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }
}