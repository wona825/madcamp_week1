package com.madcamp.phonebook.presentation.contact

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.madcamp.phonebook.domain.model.Contact
import com.madcamp.phonebook.navigation.Screen
import com.madcamp.phonebook.presentation.contact.component.SearchBox
import com.madcamp.phonebook.ui.theme.Gray200
import com.madcamp.phonebook.ui.theme.Gray400

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactListScreen(
    navController: NavController,
    contactList: List<Contact>
) {
    val screen = Screen()

    var search by rememberSaveable { mutableStateOf("") }

    // 검색된 연락처 목록
    val filteredContacts = contactList.filter {
        it.name.contains(search, ignoreCase = true) ||
                it.phoneNumber.contains(search)
    }

    val grouped = if (filteredContacts != mutableListOf(Contact())) {
        filteredContacts.groupBy {
            it.name.first().getKoreanConsonant()
        }
    } else {
        emptyMap()
    }

    val sortedGrouped = grouped.toSortedMap()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        if (contactList.isNotEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
            SearchBox(
                text = search,
                placeholder = "이름, 전화번호로 검색하기",
                onValueChange = { newText ->
                    search = newText
                }
            )
            Spacer(modifier = Modifier.height(30.dp))

            if (sortedGrouped.isNotEmpty()) {
                LazyColumn {
                    sortedGrouped.forEach { (initial, contactsForInitial) ->

                        stickyHeader {
                            CharacterHeader(initial)
                        }

                        items(contactsForInitial) { contact ->
                            ContactListItem(contact, onCLick = {
                                navController.navigate(screen.ContactDetailScreen + "/${contact.phoneNumber}")
                            })
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(Gray200)
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = "검색 결과가 존재하지 않습니다.",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    color = Gray400
                )
            }
        } else {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "No contact conntected",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                color = Gray400
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
            .padding(horizontal = 16.dp, vertical = 2.dp)
    ) {
        Text(
            text = char.toString(),
            modifier = Modifier
                .fillMaxWidth(),
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp

            )
        )
    }
}

@Composable
fun ContactListItem(
    contact: Contact,
    onCLick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCLick() },
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
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

