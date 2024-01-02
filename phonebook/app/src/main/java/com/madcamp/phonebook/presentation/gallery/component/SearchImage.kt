package com.madcamp.phonebook.presentation.gallery.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.madcamp.phonebook.MainActivity
import com.madcamp.phonebook.domain.model.Diary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchImage(){
    var searchText by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .padding(5.dp)
    ){
        Icon(imageVector = Icons.Default.Search, contentDescription = null, modifier = Modifier
            .size(40.dp)  // Adjust the size of the icon
            .padding(end = 8.dp)  // Adjust the padding if needed
            .align(Alignment.CenterVertically))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(40.dp)
        )
    }
}