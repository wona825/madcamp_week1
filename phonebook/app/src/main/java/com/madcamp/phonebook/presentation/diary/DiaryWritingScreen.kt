package com.madcamp.phonebook.presentation.diary

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.madcamp.phonebook.domain.model.Contact
import com.madcamp.phonebook.presentation.contact.contactComponent.ContactDropDownBox
import com.madcamp.phonebook.presentation.component.TextBox
import com.madcamp.phonebook.presentation.contact.viewModel.ContactViewModel
import com.madcamp.phonebook.presentation.gallery.favorites.favorites
import com.madcamp.phonebook.ui.theme.Gray100
import com.madcamp.phonebook.ui.theme.Gray400
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiaryWritingScreen(
    navController: NavController,
    favoriteList: MutableList<favorites>,
    contactViewModel: ContactViewModel,
    scrollState: ScrollState = rememberScrollState()
) {
    // Get a current year and date.
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val formattedDate = currentDate.format(formatter)

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val contactFavoriteList = contactViewModel.contactList.filter {
        it.favoriteStatus
    }

    val contactDropDownList = buildList {
        add(Contact("선택 안함", "no select"))
        addAll(if (contactFavoriteList.isNotEmpty()) contactFavoriteList else contactViewModel.contactList)
    }

    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        // Date
        Text(
            text = formattedDate,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            // Title
            TextBox(
                type = "title",
                text = title,
                width = 320.dp,
                height = 55.dp,
                boxColor = Gray100,
                onValueChange = { newText ->
                    title = newText
                },
                readOnly = false,
                fontSize = 15
            )
            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(143.dp)
                    .padding(0.dp),
                value = description,
                onValueChange = { newText ->
                    description = newText
                },
                textStyle = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold),
                label = {
                    Text(
                        text = "${description.length}/100", // 현재 입력된 문자 수를 계산하여 표시
                        color = if (description.length > 100) Color.Red else Gray400, // 100자 이상 입력 시 레이블의 색상을 변경
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Gray100
                ),
                shape = RoundedCornerShape(20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            ContactDropDownBox(itemList = contactDropDownList)
        }
    }
}
