package com.madcamp.phonebook.presentation.gallery

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.madcamp.phonebook.R
import com.madcamp.phonebook.domain.model.Diary
import com.madcamp.phonebook.navigation.Screen
import com.madcamp.phonebook.presentation.diary.viewmodel.DiaryViewModel
import com.madcamp.phonebook.ui.theme.Brown300
import com.madcamp.phonebook.ui.theme.Brown400
import com.madcamp.phonebook.ui.theme.Gray100
import com.madcamp.phonebook.ui.theme.Gray200
import com.madcamp.phonebook.ui.theme.Gray400
import com.madcamp.phonebook.ui.theme.Orange400

@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun ImageDetailScreen(
    navController: NavController,
    diaryViewModel: DiaryViewModel,
    diary: Diary,
    scrollState: ScrollState = rememberScrollState()
){
    val screen = Screen()
    val indexOfDiary = diaryViewModel.diaryList.indexOf(diary)
//    val screenWidth = getScreenWidth(LocalContext.current)

    var diaryLikeStatus by remember { mutableStateOf(diaryViewModel.diaryList[indexOfDiary].favoriteStatus) }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .background(color = Brown300)
            .padding(20.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
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
                imageVector = if (diaryLikeStatus) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                modifier = Modifier
                    .size(35.dp)
                    .clickable {
                        diaryLikeStatus = !diaryLikeStatus
                        diaryViewModel.diaryList[indexOfDiary].favoriteStatus = diaryLikeStatus
                    },
                contentDescription = "like_or_not",
                tint = Brown400
            )
        }

        Column(
            modifier = Modifier
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
                    text = diaryViewModel.diaryList[indexOfDiary].dateTime,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Monospace,
                    color = Brown400,
                    modifier = Modifier.background(Orange400)
                )

                Image(
                    painter = painterResource(id =
                    when (diary.icon) {
                        1 -> R.drawable.dog_1
                        2 -> R.drawable.dog_2
                        3 -> R.drawable.dog_3
                        4 -> R.drawable.dog_4
                        5 -> R.drawable.dog_5
                        else -> R.drawable.dear_my_logo
                    }),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "# " + diary.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif,
                color = Brown400
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                val bitmap = remember { mutableStateOf<Bitmap?>(null) }
                val imageUri = diaryViewModel.diaryList[indexOfDiary].image
                val context = LocalContext.current

                imageUri?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                    }
                    else {
                        val source = ImageDecoder.createSource(context.contentResolver, it)
                        bitmap.value = ImageDecoder.decodeBitmap(source)
                    }
                    bitmap.value?.let {btm ->
                        Image(
                            bitmap = btm.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(5.dp))
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            // Description
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(5))
                    .background(Gray200)
                    .padding(10.dp)
            ) {
                Text(
                    text = diary.description,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.SansSerif,
                    color = Brown400
                )
            }
            Spacer(modifier = Modifier.height(15.dp))

            if (diary.contact.phoneNumber != "") {
                // Tag
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Face,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { navController.navigate(screen.ContactDetailScreen + "/${diary.contact.phoneNumber}") },
                        contentDescription = "call_button",
                        tint = Brown400
                    )
                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = diary.contact.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Monospace,
                        color = Brown400,
                        modifier = Modifier.clickable {
                            navController.navigate(screen.ContactDetailScreen + "/${diary.contact.phoneNumber}")
                        }
                    )
                }
            }
        }
    }
}



