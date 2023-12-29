package com.madcamp.phonebook.presentation

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.madcamp.phonebook.domain.model.Contact
import com.madcamp.phonebook.ui.theme.Gray200

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactListScreen(
    contactList: List<Contact>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val activity = LocalContext.current as Activity

        val grouped = contactList.groupBy {
            it.name.first().getKoreanConsonant()
        }

        val sortedGrouped = grouped.toSortedMap()

        if (contactList.isNotEmpty()) {
            LazyColumn {
                sortedGrouped.forEach { (initial, contactsForInitial) ->
                    stickyHeader {
                        CharacterHeader(initial)
                    }

                    items(contactsForInitial) { contact ->
                        ContactListItem(contact)
                    }
                }
            }
        } else {
            Text(
                text = "No contact selected",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
        }
    }
}

// header 표기를 위한 변환
fun Char.getKoreanConsonant(): Char {
    return when (this) {
        in '가'..'깋' -> 'ㄱ'
        in '나'..'닣' -> 'ㄴ'
        in '다'..'딯' -> 'ㄷ'
        in '라'..'맇' -> 'ㄹ'
        in '마'..'밓' -> 'ㅁ'
        in '바'..'빟' -> 'ㅂ'
        in '사'..'싷' -> 'ㅅ'
        in '아'..'잏' -> 'ㅇ'
        in '자'..'짛' -> 'ㅈ'
        in '차'..'칳' -> 'ㅊ'
        in '카'..'킿' -> 'ㅋ'
        in '타'..'팋' -> 'ㅌ'
        in '파'..'핗' -> 'ㅍ'
        in '하'..'힣' -> 'ㅎ'
        else -> this
    }
}

// header 표기
@Composable
fun CharacterHeader(char: Char) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Gray200)
            .padding(vertical = 1.dp)
    ) {
        Text(
            text = char.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun ContactListItem(contact: Contact) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Text(
                text = "${contact.name}",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.W900,
                modifier = Modifier.padding(bottom = 7.dp)
            )
            Text(
                text = formatPhoneNumber(contact.phoneNumber),
                style = MaterialTheme.typography.body1
            )
        }
    }
}


// 전화번호를 특정 형식으로 포맷팅
fun formatPhoneNumber(phoneNumber: String): String {
    return when (phoneNumber.length) {
        10 -> {
            "${phoneNumber.substring(0, 2)}-${phoneNumber.substring(2, 5)}-${phoneNumber.substring(5)}"
        }
        11 -> {
            "${phoneNumber.substring(0, 3)}-${phoneNumber.substring(3, 7)}-${phoneNumber.substring(7)}"
        }
        else -> {
            phoneNumber
        }
    }
}

