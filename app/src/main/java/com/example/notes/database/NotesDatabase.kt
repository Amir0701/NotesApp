package com.example.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.dao.NotesDao
import com.example.notes.entities.Notes

@Database(entities = [Notes::class], version = 1)
abstract class NotesDatabase: RoomDatabase() {
    abstract fun getDao(): NotesDao

    companion object{
        private var INSTANCE: NotesDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): NotesDatabase{
            var currentInstance = INSTANCE

            if(currentInstance == null){
                currentInstance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "NoteDB"
                ).build()

                INSTANCE = currentInstance
            }

            return INSTANCE!!
        }
    }
}