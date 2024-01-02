package com.madcamp.phonebook.presentation.Diary.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.madcamp.phonebook.domain.model.Diary

class DiaryViewModel: ViewModel() {

    var diaryList by mutableStateOf<List<Diary>>(emptyList())

}