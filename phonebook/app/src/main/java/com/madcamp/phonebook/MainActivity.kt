package com.madcamp.phonebook

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.activity.compose.setContent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.madcamp.phonebook.domain.model.Contact
import com.madcamp.phonebook.navigation.NavGraph
import com.madcamp.phonebook.presentation.gallery.favorites.favorites
import com.madcamp.phonebook.presentation.contact.viewModel.ContactViewModel
import com.madcamp.phonebook.ui.theme.PhonebookTheme

class MainActivity : ComponentActivity(), ContactViewModel.PhoneCallListener, ContactViewModel.SendMessageListener {
    private lateinit var contactViewModel: ContactViewModel


    private var getlist: MutableList<favorites> =  mutableListOf<favorites>()  // list of favorites


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        contactViewModel.phoneCallListener = this
        contactViewModel.sendMessageListener = this


        setContent {
            PhonebookTheme {
                NavGraph(activity = this, contactViewModel = contactViewModel, favoriteList = getlist)
            }
        }

        if (hasContactPermission(this)) {
            retrieveContacts()
        } else {
            requestContactPermission(this)
        }
}


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, retrieve contacts
            retrieveContacts()
        }
    }

    override fun makePhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")

        startActivity(intent)
    }

    override fun sendMessage(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("smsto:$phoneNumber")

        startActivity(intent)
    }

    private fun retrieveContacts() {
        val contacts = mutableListOf<Contact>()
        val cursor: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val number: String =
                    it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val name: String =
                    it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))

                contacts.add(Contact(name, number))
            }
            contactViewModel.contactList = contacts
            contactViewModel.event()
        }
    }

    private fun hasContactPermission(context: Context): Boolean {
        return context.checkSelfPermission("android.permission.READ_CONTACTS") == PackageManager.PERMISSION_GRANTED
    }

    private fun requestContactPermission(activity: Activity) {
        if (!hasContactPermission(activity)) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf("android.permission.READ_CONTACTS"),
                1
            )
        }
    }
}

