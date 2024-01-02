package com.madcamp.phonebook.presentation.contact.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcamp.phonebook.domain.model.Contact
import com.madcamp.phonebook.domain.response.ContactResponse
import com.madcamp.phonebook.presentation.contact.repository.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ContactViewModel: ViewModel() {
    var phoneCallListener: PhoneCallListener? = null
    var sendMessageListener: SendMessageListener? = null

    var contactList by mutableStateOf<List<Contact>>(emptyList())

    private val contactFLow = MutableStateFlow<List<ContactResponse>>(listOf())

    fun event() {
        viewModelScope.launch() {
            kotlin.runCatching {
                ContactRepository.fetchUsers(contactList)
            }.onSuccess {
                contactFLow.value = it
                contactList = convertToContactList(contactFLow.value)
            }.onFailure {
                contactFLow.value = emptyList()
            }
        }
    }


    private fun convertToContactList(contactResponses: List<ContactResponse>): List<Contact> {
        return contactResponses.map {
            Contact(
                name = it.name,
                phoneNumber = it.phoneNumber,
                favoriteStatus = it.favoriteStatus
            )
        }
    }

    interface PhoneCallListener {
        fun makePhoneCall(phoneNumber: String)
    }

    interface SendMessageListener {
        fun sendMessage(phoneNumber: String)
    }

    fun initiatePhoneCall(phoneNumber: String) {
        viewModelScope.launch {
            phoneCallListener?.makePhoneCall(phoneNumber)
        }
    }

    fun initiateSendMessage(phoneNumber: String) {
        viewModelScope.launch {
            sendMessageListener?.sendMessage(phoneNumber)
        }
    }
}