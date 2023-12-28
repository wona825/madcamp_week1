package com.madcamp.phonebook.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.text.TextStyle
import com.madcamp.phonebook.domain.model.Contact
import com.madcamp.phonebook.ui.theme.Gray200

// 연락처 목록 표시 screen
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactsList(contacts: List<Contact>) {
    val grouped = contacts.groupBy {
        it.name.first().getKoreanConsonant()
    }

    val sortedGrouped = grouped.toSortedMap()

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

// 연락처 표기
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
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.W900,
                modifier = Modifier.padding(bottom = 7.dp)
            )
            Text(
                text = "${contact.phoneNumber}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// 임시로 sample 연락처 생성
fun generateSampleContacts(): List<Contact> {
    val names = listOf(
        "김철수", "나영희", "박민수", "이지훈", "정윤서",
        "송재현", "임지원", "조민아", "이승훈", "서현우",
        "김수진", "박건우", "한예진", "이동훈", "고서연",
        "황재현", "양세빈", "조현우", "송하은", "이태준"
    )

    val contacts = mutableListOf<Contact>()
    for (id in 1..names.size) {
        val name = names[id - 1]
        val phoneNumber = generateRandomPhoneNumber()
        contacts.add(Contact(id, name, phoneNumber))
    }
    return contacts
}

// 무작위 전화번호 생성
fun generateRandomPhoneNumber(): String {
    val random = java.util.Random()
    val firstSegment = "010"
    val secondSegment = String.format("%04d", random.nextInt(10000))
    val thirdSegment = String.format("%04d", random.nextInt(10000))
    return "$firstSegment-$secondSegment-$thirdSegment"
}
