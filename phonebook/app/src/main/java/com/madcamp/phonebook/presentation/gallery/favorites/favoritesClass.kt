package com.madcamp.phonebook.presentation.gallery.favorites

import android.net.Uri
import com.madcamp.phonebook.domain.model.Contact

data class favorites (
    var name: String,
    var icon: Int,
    var image: Uri?,
    var love: Boolean,
    var description: String,
    var valid: Boolean,
    var dateTime: String?
)