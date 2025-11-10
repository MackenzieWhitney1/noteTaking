package com.example.noteTaking.model
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class NoteDTORequest(
    @field:NotBlank @field:Size(max = 100)
    val title: String,
    @field:Size(max=1000)
    val description: String
)