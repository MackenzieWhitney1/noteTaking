package com.example.noteTaking.services

import com.example.noteTaking.dataSources.NoteRepository
import com.example.noteTaking.model.Note
import com.example.noteTaking.model.NoteDTORequest
import org.springframework.stereotype.Service

@Service
class NoteService(
    private val repository : NoteRepository
) {
    fun getNotes(): List<Note> = repository.findAll()
    fun getByNoteId(id: String): Note =
        repository.findById(id).orElseThrow { NoSuchElementException("Note with id $id not found") }

    fun createNote(request: NoteDTORequest): Note {
        val newNote = Note(
            title = request.title,
            description = request.description
        )
        return repository.save(newNote)
    }

    fun updateNote(id: String, request: NoteDTORequest): Note {
        val existing = getByNoteId(id)
        val updated = existing.copy(
            title = request.title,
            description = request.description
        )
        return repository.save(updated)
    }
    fun deleteNote(id: String) {
        if (!repository.existsById(id)) {
            throw NoSuchElementException("Note with id $id not found")
        }
        repository.deleteById(id)
    }
}