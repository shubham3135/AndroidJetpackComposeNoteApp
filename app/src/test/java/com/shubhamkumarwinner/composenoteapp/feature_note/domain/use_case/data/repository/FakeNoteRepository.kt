package com.shubhamkumarwinner.composenoteapp.feature_note.domain.use_case.data.repository

import com.shubhamkumarwinner.composenoteapp.feature_note.domain.model.InvalidNoteException
import com.shubhamkumarwinner.composenoteapp.feature_note.domain.model.Note
import com.shubhamkumarwinner.composenoteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.jvm.Throws

class FakeNoteRepository : NoteRepository {

    private val notes = mutableListOf<Note>()

    override fun getNotes(): Flow<List<Note>> = flow { emit(notes) }

    override suspend fun getNoteById(id: Int): Note? = notes.find { it.id == id }

    @Throws(InvalidNoteException::class)
    override suspend fun insertNote(note: Note) {
        if(note.title.isBlank()){
            throw InvalidNoteException("The title of the note can't be empty.")
        }
        if (note.content.isBlank()){
            throw InvalidNoteException("The content of the note can't be empty.")
        }
        notes.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }
}