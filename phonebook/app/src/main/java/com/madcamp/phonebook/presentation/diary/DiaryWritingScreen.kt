package com.madcamp.phonebook.presentation.diary

import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.madcamp.phonebook.R
import com.madcamp.phonebook.domain.model.Contact
import com.madcamp.phonebook.domain.model.Diary
import com.madcamp.phonebook.presentation.diary.component.ChooseImage
import com.madcamp.phonebook.presentation.diary.component.IconDropBox
import com.madcamp.phonebook.presentation.diary.viewmodel.DiaryViewModel
import com.madcamp.phonebook.presentation.component.TextBox
import com.madcamp.phonebook.presentation.contact.contactComponent.ContactDropDownBox
import com.madcamp.phonebook.presentation.contact.viewModel.ContactViewModel
import com.madcamp.phonebook.presentation.gallery.component.getScreenWidth
import com.madcamp.phonebook.ui.theme.Brown200
import com.madcamp.phonebook.ui.theme.Brown300
import com.madcamp.phonebook.ui.theme.Brown400
import com.madcamp.phonebook.ui.theme.Gray100
import com.madcamp.phonebook.ui.theme.Orange400
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiaryWritingScreen(
    navController: NavController,
    diaryViewModel: DiaryViewModel,
    contactViewModel: ContactViewModel,
    scrollState: ScrollState = rememberScrollState()
) {
    // Get a current year and date.
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val formattedDate = currentDate.format(formatter)

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var like by remember {mutableStateOf(false)}
    val iconValue: MutableState<Int> = remember {mutableStateOf(-1)}
    val imageUriValue: MutableState<Uri?> = remember {mutableStateOf(null)}
    var contact: Contact =  remember { Contact("", "", false) }
    val context = LocalContext.current

    val contactFavoriteList = contactViewModel.contactList.filter {
        it.favoriteStatus
    }

    val contactDropDownList = buildList {
        add(Contact("선택 안함", "no select"))
        addAll(if (contactFavoriteList.isNotEmpty()) contactFavoriteList else contactViewModel.contactList)
    }

    val newDiary = Diary("", iconValue.value, imageUriValue.value, like, "", formattedDate, contact)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Brown300)
            .padding(15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                modifier = Modifier
                    .size(50.dp)
                    .clickable { navController.popBackStack() },
                contentDescription = "back_button",
                tint = Brown400
            )

            Image(
                painterResource(id = R.drawable.dear_my_logo),
                contentDescription = "dear_my_logo",
                modifier = Modifier.size(100.dp)
            )

            Icon(
                imageVector = if(like) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = Brown400,
                modifier = Modifier
                    .size(35.dp)
                    .clickable { like = !like }
            )
        }

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .clip(RoundedCornerShape(5))
                .border(2.dp, Brown400, RoundedCornerShape(5))
                .background(Gray100)
                .padding(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formattedDate,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Monospace,
                    color = Brown400,
                    modifier = Modifier.background(Orange400)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "☁️ 오늘 하루를 한마디로 표현해주세요",
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif,
                color = Brown400
            )
            Spacer(modifier = Modifier.height(3.dp))

            TextBox(
                type = "title",
                text = title,
                width = getScreenWidth(context).dp,
                height = 55.dp,
                boxColor = Gray100,
                onValueChange = { newText ->
                    title = newText
                },
                readOnly = false,
                fontSize = 15
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "☁️ 오늘 당신의 감정을 알려주세요",
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif,
                color = Brown400
            )
            Spacer(modifier = Modifier.height(10.dp))

            IconDropBox(iconValue)

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "☁️ 오늘 하루를 나타내는 사진을 골라주세요",
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif,
                color = Brown400
            )
            Spacer(modifier = Modifier.height(3.dp))

            ChooseImage(imageUriValue)

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "☁️ 오늘 하루를 조금 더 자세히 알려주세요",
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif,
                color = Brown400
            )
            Spacer(modifier = Modifier.height(3.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp)
                    .clip(RoundedCornerShape(10))
                    .border(1.dp, Brown400, RoundedCornerShape(10))
                    .padding(0.dp),
                value = description,
                onValueChange = { newText ->
                    description = newText
                },
                textStyle = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold),
                label = {
                    Text(
                        text = "${description.length}/100", // 현재 입력된 문자 수를 계산하여 표시
                        color = if (description.length > 100) Color.Red else Brown300, // 50자 이상 입력 시 레이블의 색상을 변경
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
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "☁️ 함께한 친구를 태그해주세요",
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif,
                color = Brown400
            )
            Spacer(modifier = Modifier.height(3.dp))

            contact = ContactDropDownBox(itemList = contactDropDownList)

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .clip(RoundedCornerShape(20))
                    .background(Brown400)
                    .border(1.dp, Color.Transparent, RoundedCornerShape(4))
                    .clickable {
                        if (newDiary.image == null) {
                            Toast.makeText(context, "사진을 추가해주세요!", Toast.LENGTH_SHORT).show()
                        } else if (newDiary.name == "") {
                            Toast.makeText(context, "제목을 추가해주세요!", Toast.LENGTH_SHORT).show()
                        } else if (newDiary.description == "") {
                            Toast.makeText(context, "설명을 추가해주세요!", Toast.LENGTH_SHORT).show()
                        } else {
                            newDiary.image?.let{
                                if(iconValue.value != -1) {
                                    newDiary.name = title
                                    newDiary.description = description
                                    newDiary.icon = iconValue.value
                                    newDiary.dateTime = formattedDate
                                    newDiary.favoriteStatus = like
                                    newDiary.image = imageUriValue.value
                                    if ((contact.name == "선택 안함") || (contact.phoneNumber == "선택 안함")) {
                                        contact = Contact()
                                    }
                                    newDiary.contact = contact
                                    diaryViewModel.diaryList += newDiary
                                    navController.popBackStack()
                                } else{
                                    Toast.makeText(context, "감정 이모티콘을 선택해주세요!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    modifier = Modifier.size(30.dp),
                    contentDescription = "add_diary",
                    tint = Brown200
                )
            }
        }
    }
}