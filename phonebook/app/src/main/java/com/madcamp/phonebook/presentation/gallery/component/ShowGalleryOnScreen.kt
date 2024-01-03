package com.madcamp.phonebook.presentation.gallery.component

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.madcamp.phonebook.MainActivity
import com.madcamp.phonebook.R
import com.madcamp.phonebook.domain.model.Diary
import com.madcamp.phonebook.presentation.diary.viewmodel.DiaryViewModel


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun ShowGalleryOnScreen(navController: NavController, diaryViewModel: DiaryViewModel){

    val diaryList = diaryViewModel.diaryList
        for (index in diaryViewModel.diaryList.indices step 3) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                //horizontalArrangement = Arrangement.SpaceBetween // Arrange child elements with even space
            ) {


                // Elem1 (Item)
                ShowItemOnScreen(
                    navController,
                    diaryList[index],
                    diaryViewModel
                )

                // Elem2. Second Item
                if ((index + 1) == diaryList.size) {
                    // do nothing

                } else if ((index + 2) == diaryList.size) {
                    ShowItemOnScreen(
                        navController,
                        diaryList[index + 1],
                        diaryViewModel
                    )
                } else {
                    ShowItemOnScreen(
                        navController,
                        diaryList[index + 1],
                        diaryViewModel
                    )
                    ShowItemOnScreen(
                        navController,
                        diaryList[index + 2],
                        diaryViewModel
                    )
                }


        }
    }
}


// Show each item on the screen. Each item consists of image and the description. (2 elements)
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun ShowItemOnScreen(navController: NavController, diary: Diary, diaryViewModel: DiaryViewModel){

    val screenWidth = (getScreenWidth(LocalContext.current).toDouble() / 3).toInt()

    Column{
        // Elem1. image
        ShowImage(navController, diary, diaryViewModel)
        // Elem2. text
        Box(
            modifier = Modifier
                .width(screenWidth.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Row() {
                Box(
                    modifier = Modifier
                        .weight(4f)
                ) {
                    ShowImageInformation(diary, diaryViewModel)
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    when (diary.icon) {
                        1 -> Image(painter = painterResource(id = R.drawable.dog_1), contentDescription = null, modifier = Modifier.size(30.dp))
                        2 -> Image(painter = painterResource(id = R.drawable.dog_2), contentDescription = null, modifier = Modifier.size(30.dp))
                        3 -> Image(painter = painterResource(id = R.drawable.dog_3), contentDescription = null, modifier = Modifier.size(30.dp))
                        4 -> Image(painter = painterResource(id = R.drawable.dog_4), contentDescription = null, modifier = Modifier.size(30.dp))
                        5 -> Image(painter = painterResource(id = R.drawable.dog_5), contentDescription = null, modifier = Modifier.size(30.dp))
                        else -> {}
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                        Icon(
                            imageVector = if (diary.love) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize() // Adjust the size of the icon
                                .align(Alignment.Center)
                        )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.R)
fun getScreenWidth(context: Context): Int {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val displayMetrics = DisplayMetrics()
    context.display?.getRealMetrics(displayMetrics)
    return ((displayMetrics.widthPixels).toDouble() / 3).toInt()
}


