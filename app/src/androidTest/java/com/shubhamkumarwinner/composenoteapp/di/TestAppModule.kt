package com.shubhamkumarwinner.composenoteapp.di

import android.app.Application
import androidx.room.Room
import com.shubhamkumarwinner.composenoteapp.feature_note.data.data_source.NoteDatabase
import com.shubhamkumarwinner.composenoteapp.feature_note.data.repository.NoteRepositoryImpl
import com.shubhamkumarwinner.composenoteapp.feature_note.domain.repository.NoteRepository
import com.shubhamkumarwinner.composenoteapp.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app:Application): NoteDatabase{
        return Room.inMemoryDatabaseBuilder(
            app,
            NoteDatabase::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository{
        return NoteRepositoryImpl(db.noteDao)
    }
    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository):NoteUseCases{
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository = repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            insertNoteUseCase = AddNoteUseCase(repository),
            getNoteUseCase = GetNoteUseCase(repository)
        )
    }
}