package com.madcamp.phonebook.presentation.Diary

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.madcamp.phonebook.R
import com.madcamp.phonebook.domain.model.Diary
import com.madcamp.phonebook.navigation.Screen
import com.madcamp.phonebook.presentation.Diary.component.ShowIconForEachDay
import com.madcamp.phonebook.presentation.Diary.viewmodel.DiaryViewModel
import com.madcamp.phonebook.presentation.gallery.component.getScreenWidth
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiaryBeginScreen(navController: NavController, diaryViewModel: DiaryViewModel){

    val screen = Screen()
    val diaryList = diaryViewModel.diaryList

    // Get a current year and date.
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val formattedDate = currentDate.format(formatter)
    val formattedDateList = formattedDate.split(".")
    val currentYear = formattedDateList[0]
    val currentMonth = formattedDateList[1]

        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp)
        ){

            // Year
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(text = currentYear, fontSize = 15.sp)
            }

            // Month
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(text = currentMonth, fontSize = 45.sp)
            }

            Box(
                modifier = Modifier.fillMaxWidth().weight(6f).padding(top = 10.dp)
            ){
                ShowIconForEachDay(diaryList)
            }

            // Button to write a journal
            Box(
                modifier = Modifier.fillMaxWidth().weight(2f),
                contentAlignment = Alignment.BottomCenter
            ){
                FilledTonalButton(

                    onClick = {
                        navController.navigate(screen.DiaryWritingScreen)
                    },
                    modifier = Modifier.align(Alignment.Center)

                ) {
                    Text("소중한 오늘 기록하기")
                }
            }

        }
}