package com.madcamp.phonebook.presentation.diary.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import com.madcamp.phonebook.R
import com.madcamp.phonebook.domain.model.Diary
import com.madcamp.phonebook.presentation.gallery.component.getScreenWidth
import com.madcamp.phonebook.ui.theme.Brown200
import com.madcamp.phonebook.ui.theme.Brown400
import com.madcamp.phonebook.ui.theme.Gray200
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
    val line = if ((numberOfDiaries / 5) < 4) {numberOfDiaries/5} else 4
    val residue = numberOfDiaries % 5
    val screenWidth = getScreenWidth(LocalContext.current)
    val stampWidth = ((screenWidth.toDouble())/7).toInt()
    val imageWidth = ((screenWidth.toDouble())/9).toInt()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .clip(RoundedCornerShape(5))
            .background(Gray200)
            .padding(10.dp)
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
                            1 ->
                                Box(
                                    modifier = Modifier
                                        .size(stampWidth.dp)
                                        .background(Color.White, shape = CircleShape)
                                        .border(1.dp, Brown400, shape = CircleShape)
                                    ,
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.dog_1),
                                        contentDescription = null,
                                        modifier = Modifier.size(imageWidth.dp)
                                    )
                                }


                            2 -> Box(
                                modifier = Modifier
                                    .size(stampWidth.dp)
                                    .background(Color.White, shape = CircleShape)
                                    .border(1.dp, Brown400, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.dog_2),
                                    contentDescription = null,
                                    modifier = Modifier.size(imageWidth.dp)
                                )
                            }

                            3 -> Box(
                                modifier = Modifier
                                    .size(stampWidth.dp)
                                    .background(Color.White, shape = CircleShape)
                                    .border(1.dp, Brown400, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.dog_3),
                                    contentDescription = null,
                                    modifier = Modifier.size(imageWidth.dp)
                                )
                            }

                            4 -> Box(
                                modifier = Modifier
                                    .size(stampWidth.dp)
                                    .background(Color.White, shape = CircleShape)
                                    .border(1.dp, Brown400, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.dog_4),
                                    contentDescription = null,
                                    modifier = Modifier.size(imageWidth.dp)
                                )
                            }

                            5 -> Box(
                                modifier = Modifier
                                    .size(stampWidth.dp)
                                    .background(Color.White, shape = CircleShape)
                                    .border(1.dp, Brown400, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.dog_5),
                                    contentDescription = null,
                                    modifier = Modifier.size(imageWidth.dp)
                                )
                            }

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
                            1 ->
                                Box(
                                    modifier = Modifier
                                        .size(stampWidth.dp)
                                        .background(Color.White, shape = CircleShape)
                                        .border(1.dp, Brown400, shape = CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.dog_1),
                                        contentDescription = null,
                                        modifier = Modifier.size(imageWidth.dp)
                                    )
                                }


                            2 -> Box(
                                modifier = Modifier
                                    .size(stampWidth.dp)
                                    .background(Color.White, shape = CircleShape)
                                    .border(1.dp, Brown400, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.dog_2),
                                    contentDescription = null,
                                    modifier = Modifier.size(imageWidth.dp)
                                )
                            }

                            3 -> Box(
                                modifier = Modifier
                                    .size(stampWidth.dp)
                                    .background(Color.White, shape = CircleShape)
                                    .border(1.dp, Brown400, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.dog_3),
                                    contentDescription = null,
                                    modifier = Modifier.size(imageWidth.dp)
                                )
                            }

                            4 -> Box(
                                modifier = Modifier
                                    .size(stampWidth.dp)
                                    .background(Color.White, shape = CircleShape)
                                    .border(1.dp, Brown400, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.dog_4),
                                    contentDescription = null,
                                    modifier = Modifier.size(imageWidth.dp)
                                )
                            }

                            5 -> Box(
                                modifier = Modifier
                                    .size(stampWidth.dp)
                                    .background(Color.White, shape = CircleShape)
                                    .border(1.dp, Brown400, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.dog_5),
                                    contentDescription = null,
                                    modifier = Modifier.size(imageWidth.dp)
                                )
                            }

                            else -> {}
                        }
                    }
                    else{
                        Box(
                            modifier = Modifier
                                .size(stampWidth.dp)
                                .background(Color.White, shape = CircleShape)
                                .border(1.dp, Brown400, shape = CircleShape)
                        ) {
                        }
                    }
                }
            }
        }

        for (lines in 0 until 4 - line - 1) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                for (i in 0 until 5) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)) {
                        Box(
                            modifier = Modifier
                                .size(stampWidth.dp)
                                .background(Color.White, shape = CircleShape)
                                .border(1.dp, Brown400, shape = CircleShape)
                        ) {
                        }
                    }
                }
            }
        }


    }
    filteredDiaryList = emptyList()
}