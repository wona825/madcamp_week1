package com.madcamp.phonebook.domain.model

import android.net.Uri

data class Diary(
    var name: String,
    var icon: Int,
    var image: Uri?,
    var love: Boolean,
    var description: String,
    var dateTime: String,
    var contact: Contact
)
