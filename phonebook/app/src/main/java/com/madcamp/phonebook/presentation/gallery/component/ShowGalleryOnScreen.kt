package com.madcamp.phonebook.presentation.gallery.component

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.madcamp.phonebook.domain.model.Diary
import com.madcamp.phonebook.ui.theme.Gray400


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun ShowGalleryOnScreen(
    navController: NavController,
    diaryList: List<Diary>,
    search: String,
    checkFavoriteStatus: Boolean
) {
    if (diaryList.isNotEmpty()) {
        for (index in diaryList.indices step 3) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                // Elem1 (Item)
                ShowImage(
                    navController,
                    diaryList[index],
                    diaryList
                )

                // Elem2. Second Item
                if ((index + 1) == diaryList.size) {
                    // do nothing

                } else if ((index + 2) == diaryList.size) {
                    ShowImage(
                        navController,
                        diaryList[index + 1],
                        diaryList
                    )
                } else {
                    ShowImage(
                        navController,
                        diaryList[index + 1],
                        diaryList
                    )
                    ShowImage(
                        navController,
                        diaryList[index + 2],
                        diaryList
                    )
                }
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "검색 결과가 존재하지 않습니다.",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                color = Gray400
            )
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


