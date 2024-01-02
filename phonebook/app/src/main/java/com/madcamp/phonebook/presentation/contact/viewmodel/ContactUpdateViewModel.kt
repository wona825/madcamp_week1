package com.madcamp.phonebook.presentation.contact.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcamp.phonebook.domain.model.Contact
import com.madcamp.phonebook.domain.response.ContactResponse
import com.madcamp.phonebook.presentation.contact.repository.ContactUpdateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ContactUpdateViewModel(val contact: Contact, val contactViewModel: ContactViewModel): ViewModel() {

    private val contactUpdateFLow = MutableStateFlow<List<ContactResponse>>(listOf())

    fun event(type: Int) {
        viewModelScope.launch() {
            kotlin.runCatching {
                ContactUpdateRepository.fetchUsers(contact, type)
            }.onSuccess {
                contactUpdateFLow.value = it
                contactViewModel.event()
            }.onFailure {
                contactUpdateFLow.value = emptyList()
            }
        }
    }
}