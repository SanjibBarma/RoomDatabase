package com.example.roomdatabasecrud.Repository

import com.example.roomdatabasecrud.Dao.Note
import com.example.roomdatabasecrud.Dao.NoteDao

class NoteRepository(private val noteDao: NoteDao) {
    suspend fun insert(note: Note){
        noteDao.insert(note)
    }

    suspend fun update(note: Note){
        noteDao.update(note)
    }

    suspend fun delete(note: Note){
        noteDao.delete(note)
    }

    suspend fun getAllNotes(): List<Note>{
        return noteDao.getAllNotes()
    }
}