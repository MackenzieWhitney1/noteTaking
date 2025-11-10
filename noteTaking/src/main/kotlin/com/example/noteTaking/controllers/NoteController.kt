package com.example.noteTaking.controllers

import com.example.noteTaking.model.Note
import com.example.noteTaking.model.NoteDTORequest
import com.example.noteTaking.services.NoteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@RestController
@RequestMapping("/notes")
class NoteController(private val noteService: NoteService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException) : ResponseEntity<String>{
        return ResponseEntity(e.message ?: "Note not found", HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException) : ResponseEntity<String>{
        return ResponseEntity(e.message ?: "Invalid input", HttpStatus.BAD_REQUEST)
    }

    @GetMapping
    fun getNotes(): ResponseEntity<List<Note>> = ResponseEntity.ok(noteService.getNotes())

    @GetMapping("/{id}")
    fun getNoteById(@PathVariable id: String): ResponseEntity<Note> =
        ResponseEntity.ok(noteService.getByNoteId(id))

    @PostMapping
    open fun createNote(@RequestBody request: NoteDTORequest) : ResponseEntity<Note> {
        val createdNote = noteService.createNote(request)
        return ResponseEntity(createdNote, HttpStatus.CREATED)
    }

    @PatchMapping("/{id}")
    fun patchNote(@PathVariable id: String, @RequestBody request: NoteDTORequest) : ResponseEntity<Note> {
        val updatedNote = noteService.updateNote(id, request)
        return ResponseEntity.ok(updatedNote)
    }

    @DeleteMapping("/{id}")
    fun deleteNote(@PathVariable id : String) : ResponseEntity<Void> {
        noteService.deleteNote(id)
        return ResponseEntity.noContent().build()
    }
}