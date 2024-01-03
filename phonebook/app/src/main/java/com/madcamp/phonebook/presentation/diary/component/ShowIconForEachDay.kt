package com.madcamp.phonebook.presentation.diary.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.madcamp.phonebook.R
import com.madcamp.phonebook.domain.model.Diary
import com.madcamp.phonebook.navigation.Screen
import com.madcamp.phonebook.presentation.diary.viewmodel.DiaryViewModel
import com.madcamp.phonebook.presentation.gallery.component.getScreenWidth
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowIconForEachDay(diaryList: List<Diary>){

    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val formattedDate = currentDate.format(formatter)
    val formattedDateList = formattedDate.split(".")
    val currentYear = formattedDateList[0]
    val currentMonth = formattedDateList[1]

    var filteredDiaryList by remember { mutableStateOf(emptyList<Diary>()) }
    diaryList.forEach{
        diary ->  diary.dateTime?.let{
                dt -> if(dt.contains(currentYear + "." + currentMonth)){
                    filteredDiaryList += diary
                }
            }
    }

    val numberOfDiaries  = filteredDiaryList.size
    val line = numberOfDiaries / 5
    val residue = numberOfDiaries % 5
    val screenWidth = getScreenWidth(LocalContext.current)
    val stampWidth = ((screenWidth.toDouble())/7).toInt()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        for (lines in 0 until line) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                for (i in 0 until 5) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)) {
                        val icon = filteredDiaryList[(5 * lines + i)].icon
                        when (icon) {
                            1 -> Image(
                                painter = painterResource(id = R.drawable.dog_1),
                                contentDescription = null,
                                modifier = Modifier.size(stampWidth.dp)
                            )

                            2 -> Image(
                                painter = painterResource(id = R.drawable.dog_2),
                                contentDescription = null,
                                modifier = Modifier.size(stampWidth.dp)
                            )

                            3 -> Image(
                                painter = painterResource(id = R.drawable.dog_3),
                                contentDescription = null,
                                modifier = Modifier.size(stampWidth.dp)
                            )

                            4 -> Image(
                                painter = painterResource(id = R.drawable.dog_4),
                                contentDescription = null,
                                modifier = Modifier.size(stampWidth.dp)
                            )

                            5 -> Image(
                                painter = painterResource(id = R.drawable.dog_5),
                                contentDescription = null,
                                modifier = Modifier.size(stampWidth.dp)
                            )

                            else -> {}
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            (0..4).forEach { index ->
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)) {
                    if (index < residue) {
                        val icon = filteredDiaryList[(5 * line + index)].icon
                        when (icon) {
                            1 -> Image(
                                painter = painterResource(id = R.drawable.dog_1),
                                contentDescription = null,
                                modifier = Modifier.size(stampWidth.dp)
                            )

                            2 -> Image(
                                painter = painterResource(id = R.drawable.dog_2),
                                contentDescription = null,
                                modifier = Modifier.size(stampWidth.dp)
                            )

                            3 -> Image(
                                painter = painterResource(id = R.drawable.dog_3),
                                contentDescription = null,
                                modifier = Modifier.size(stampWidth.dp)
                            )

                            4 -> Image(
                                painter = painterResource(id = R.drawable.dog_4),
                                contentDescription = null,
                                modifier = Modifier.size(stampWidth.dp)
                            )

                            5 -> Image(
                                painter = painterResource(id = R.drawable.dog_5),
                                contentDescription = null,
                                modifier = Modifier.size(stampWidth.dp)
                            )

                            else -> {}
                        }
                    }
                }
            }
        }
    }
    filteredDiaryList = emptyList()
}