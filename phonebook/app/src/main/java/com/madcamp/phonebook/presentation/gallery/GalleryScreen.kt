package com.madcamp.phonebook.presentation.gallery

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.madcamp.phonebook.domain.model.Contact
import com.madcamp.phonebook.presentation.gallery.component.ShowGalleryOnScreen
import com.madcamp.phonebook.presentation.component.SearchBox
import com.madcamp.phonebook.presentation.contact.viewModel.ContactViewModel
import com.madcamp.phonebook.presentation.diary.viewmodel.DiaryViewModel
import com.madcamp.phonebook.ui.theme.Brown400

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun GalleryScreen(
    navController: NavController,
    diaryViewModel: DiaryViewModel
){
    var search by rememberSaveable { mutableStateOf("") }

    var checkFavoriteStatus by rememberSaveable { mutableStateOf(false) }

    val diaryList = diaryViewModel.diaryList.filter {
        it.name.contains(search, ignoreCase = true) || it.description.contains(search, ignoreCase = true) ||
                it.dateTime.contains(search) || it.contact.name.contains(search) || it.contact.phoneNumber.contains(search)
    }

    val filteredDiaryList = if (checkFavoriteStatus) {
        diaryList.filter {
            it.favoriteStatus
        }
    } else {
        diaryList
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        SearchBox(
            text = search,
            placeholder = "제목, 내용, 날짜, 연락처로 검색하기",
            onValueChange = { newText ->
                search = newText
            }
        )
        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                modifier = Modifier
                    .size(13.dp)
                    .clickable {
                        checkFavoriteStatus = !checkFavoriteStatus
                    },
                contentDescription = "check_favorite_status_contacts",
                tint = if (checkFavoriteStatus) Brown400 else Color.Gray
            )
            Spacer(modifier = Modifier.width(3.dp))

            Text(
                text = "즐겨찾기만 보기",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (checkFavoriteStatus) Brown400 else Color.Gray,
                modifier = Modifier.clickable { checkFavoriteStatus = !checkFavoriteStatus }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier.weight(8f)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(0.dp, 0.dp)
            ) {
                item {
                    ShowGalleryOnScreen(navController, filteredDiaryList, search, checkFavoriteStatus)
                }
            }
        }
    }
}