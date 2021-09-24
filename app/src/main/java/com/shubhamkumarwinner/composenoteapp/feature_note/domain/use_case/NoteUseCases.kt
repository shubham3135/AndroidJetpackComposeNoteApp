package com.shubhamkumarwinner.composenoteapp.feature_note.domain.use_case

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val insertNoteUseCase: AddNoteUseCase,
    val getNoteUseCase: GetNoteUseCase
)
