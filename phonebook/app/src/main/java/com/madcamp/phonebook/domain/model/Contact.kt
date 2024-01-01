package com.madcamp.phonebook.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Contact(
    var name: String = "",
    var phoneNumber: String = "",
    var favoriteStatus: Boolean = false
)