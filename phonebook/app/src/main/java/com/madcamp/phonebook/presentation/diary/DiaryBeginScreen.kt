package com.madcamp.phonebook.presentation.diary

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.madcamp.phonebook.R
import com.madcamp.phonebook.navigation.Screen
import com.madcamp.phonebook.presentation.diary.component.ShowIconForEachDay
import com.madcamp.phonebook.presentation.diary.viewmodel.DiaryViewModel
import com.madcamp.phonebook.presentation.gallery.component.getScreenWidth
import com.madcamp.phonebook.ui.theme.Brown200
import com.madcamp.phonebook.ui.theme.Brown300
import com.madcamp.phonebook.ui.theme.Brown400
import com.madcamp.phonebook.ui.theme.Orange400
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
    val screenWidth = getScreenWidth(LocalContext.current)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Image(
                painter = painterResource(id = R.drawable.diarybook),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 150.dp, height = 55.dp)
            )

            Text(
                text = currentYear + "." + currentMonth,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif,
                color = Brown400
            )
        }

        ShowIconForEachDay(diaryList)

        Spacer(modifier = Modifier.height(70.dp))

        Text(
            text = "오늘 하루는 어땠나요?",
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif,
            color = Brown400
        )
        Spacer(modifier = Modifier.height(5.dp))

        // Button to write a journal
        Box(
            modifier = Modifier
                .align(Alignment.End)
                .fillMaxWidth()
                .height(55.dp)
                .clip(RoundedCornerShape(50))
                .background(Brown400)
                .clickable {
                    navController.navigate(screen.DiaryWritingScreen)
                }
                .border(1.dp, Color.Transparent, RoundedCornerShape(4))
            ,
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                modifier = Modifier.size(40.dp),
                contentDescription = "Write a Diary",
                tint = Brown200
            )
        }
        Spacer(modifier = Modifier.height(15.dp))

    }
}