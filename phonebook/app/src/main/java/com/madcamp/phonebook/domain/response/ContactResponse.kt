package com.madcamp.phonebook.domain.response

import kotlinx.serialization.Serializable

@Serializable
data class ContactResponse(
    val id: Int,
    val name: String,
    val phoneNumber: String,
    val favoriteStatus: Boolean
)
