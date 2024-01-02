package com.madcamp.phonebook.presentation.Diary

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.madcamp.phonebook.navigation.Screen
import com.madcamp.phonebook.presentation.Diary.component.ShowIconForEachDay
import com.madcamp.phonebook.presentation.gallery.favorites.favorites
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiaryBeginScreen(navController: NavController, favoriteList: MutableList<favorites>){

    val screen = Screen()

    // Get a current year and date.
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val formattedDate = currentDate.format(formatter)
    val formattedDateList = formattedDate.split(".")
    val currentYear = formattedDateList[0]
    val currentMonth = formattedDateList[1]
    val currentDay = formattedDateList[2]

    Box(){
        Column(){

            // Year
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
            ){
                Text(text = currentYear, modifier = Modifier.align(Alignment.BottomCenter), fontSize = 15.sp)
            }

            // Month
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
            ){
                Text(text = currentMonth, modifier = Modifier.align(Alignment.BottomCenter), fontSize = 45.sp)
            }

            // Emtpy Space
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
            ){
            }


            //Date Diary
            for(week_index in 0 until 5){
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                        .fillMaxSize(),
                ){
                    ShowIconForEachDay(week = week_index, favoriteList = favoriteList, navController)
                }
            }

            // Empty Space
            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxSize(),
            ){

            }

            // Button to write a journal
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
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
}