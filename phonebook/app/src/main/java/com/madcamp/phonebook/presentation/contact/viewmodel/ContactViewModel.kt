package com.madcamp.phonebook.presentation.contact.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ContactViewModel : ViewModel() {
    var phoneCallListener: PhoneCallListener? = null
    var sendMessageListener: SendMessageListener? = null
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