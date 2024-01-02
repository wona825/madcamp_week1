package com.madcamp.phonebook.presentation.Diary

import android.app.AlertDialog
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.madcamp.phonebook.MainActivity
import com.madcamp.phonebook.domain.model.Contact
import com.madcamp.phonebook.domain.model.Diary
import com.madcamp.phonebook.navigation.Screen
import com.madcamp.phonebook.presentation.Diary.component.ChooseImage
import com.madcamp.phonebook.presentation.Diary.component.IconDropBox
import com.madcamp.phonebook.presentation.Diary.viewmodel.DiaryViewModel
import com.madcamp.phonebook.presentation.component.TextBox
import com.madcamp.phonebook.presentation.contact.contactComponent.ContactDropDownBox
import com.madcamp.phonebook.presentation.contact.viewModel.ContactViewModel
import com.madcamp.phonebook.ui.theme.Gray100
import com.madcamp.phonebook.ui.theme.Gray400
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
    var iconValue: MutableState<Int> = remember {mutableStateOf(-1)}
    var imageUriValue: MutableState<Uri?> = remember {mutableStateOf(null)}
    var contact: MutableState<Contact> =  remember {mutableStateOf(Contact("", "", false))}
    val context = LocalContext.current
    val screen = Screen()

    val contactFavoriteList = contactViewModel.contactList.filter {
        it.favoriteStatus
    }

    val contactDropDownList = buildList {
        add(Contact("선택 안함", "no select"))
        addAll(if (contactFavoriteList.isNotEmpty()) contactFavoriteList else contactViewModel.contactList)
    }


    val newDiary = Diary("", iconValue.value, imageUriValue.value, like, "", formattedDate, contact.value)

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

            // Icon
            IconDropBox(iconValue)

            Spacer(modifier = Modifier.height(10.dp))

            // Image
            ChooseImage(imageUriValue)
            newDiary.image = imageUriValue.value

            Spacer(modifier = Modifier.height(10.dp))

            // Contact Tag, LikeOrNot
            Row(){
                Box(modifier = Modifier.weight(6f)){ ContactDropDownBox(itemList = contactDropDownList, contact) }
                Box(modifier = Modifier
                    .weight(1f)
                    .padding(top = 15.dp), contentAlignment = Alignment.BottomCenter){
                    Icon(
                        imageVector = if(like) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { like = !like }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Description
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

            // Okay Button
            Box(modifier = Modifier.fillMaxSize()) {
                FilledTonalButton(
                    onClick = {

                        newDiary.image?.let{
                            if(iconValue.value != -1) {
                                newDiary.name = title
                                newDiary.description = description
                                newDiary.icon = iconValue.value
                                newDiary.dateTime = formattedDate
                                newDiary.contact = contact.value
                                diaryViewModel.diaryList += newDiary
                                navController.popBackStack()
                            }
                            else{
                                Toast.makeText(context, "Please add an icon", Toast.LENGTH_SHORT).show()
                            }
                        }
                        if (newDiary.image == null){
                            Toast.makeText(context, "Please add an image", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text("Add to My Journal")
                }
            }
        }
    }
}
