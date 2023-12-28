package com.madcamp.phonebook.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Contact(
    val id: Int,
    val name: String,
    val phoneNumber: String
)
