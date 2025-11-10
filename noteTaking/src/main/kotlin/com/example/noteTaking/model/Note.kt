package com.example.noteTaking.model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "notes")
data class Note (
    @Id
    @Column(nullable = false, updatable = false)
    val id: String = UUID.randomUUID().toString(),
    @Column(nullable = false, length = 200)
    val title: String,
    @Column(nullable = false, length = 2000)
    val description: String) {
    protected constructor() : this("", "", "")
}