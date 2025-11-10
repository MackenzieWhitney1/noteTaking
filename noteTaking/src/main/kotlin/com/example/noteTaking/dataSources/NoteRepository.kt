package com.example.noteTaking.dataSources

import com.example.noteTaking.model.Note
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository : JpaRepository<Note, String>

/* old boilerplate
@Repository
class NoteRepository {
    private val notes = mutableListOf<Note>(
        Note(title = "title", description = "description")
    )

    fun findAll() : List<Note> = notes.toList()

    fun findById(id: String): Note =
        notes.firstOrNull {it.id == id} ?:
        throw NoSuchElementException("No note found with id: ${id}")

    fun save(note: Note): Note {
        if (notes.any { it.id == note.id }) {
            throw IllegalArgumentException("A note with id ${note.id} already exists.")
        }
        notes.add(note)
        return note
    }

    fun update(id: String, updatedNote: Note) : Note {
        val index = notes.indexOfFirst { it.id == id }
        if (index == -1) throw NoSuchElementException("No note found with id: $id")
        notes[index] = updatedNote
        return updatedNote
    }

    fun delete(id: String) {
        val existing = findById(id)
        notes.remove(existing)
    }

}*/
