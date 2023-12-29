package com.madcamp.phonebook

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.provider.ContactsContract
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.madcamp.phonebook.ui.theme.PhonebookTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.ui.unit.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.accompanist.pager.*
import com.madcamp.phonebook.domain.model.Contact
import com.madcamp.phonebook.presentation.TabLayout

class MainActivity : ComponentActivity() {
    var contactList by mutableStateOf<List<Contact>>(emptyList())

    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhonebookTheme {
                TabLayout(contactList)
            }
        }

        // Check contacts permission and retrieve contacts
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
            contactList = contacts
        }
    }

    private fun hasContactPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            "android.permission.READ_CONTACTS"
        ) == PackageManager.PERMISSION_GRANTED
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

