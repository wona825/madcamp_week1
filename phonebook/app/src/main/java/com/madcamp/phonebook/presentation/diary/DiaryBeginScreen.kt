package com.madcamp.phonebook.presentation.diary

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.madcamp.phonebook.navigation.Screen
import com.madcamp.phonebook.presentation.diary.component.ShowIconForEachDay
import com.madcamp.phonebook.presentation.diary.viewmodel.DiaryViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("SuspiciousIndentation")
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
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // Year
            Text(
                text = currentYear,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(2.dp))

            // Month
            Text(
                text = currentMonth,
                fontSize = 35.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(10.dp))

            ShowIconForEachDay(diaryList)


//            Box(
//                modifier = Modifier.fillMaxWidth().weight(6f).padding(top = 10.dp)
//            ){
//                ShowIconForEachDay(diaryList)
//            }

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