package com.madcamp.phonebook.presentation.gallery.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.madcamp.phonebook.domain.model.Diary
import com.madcamp.phonebook.presentation.diary.viewmodel.DiaryViewModel


// Get an image name from user.
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ShowImageInformation(diary: Diary, diaryViewModel: DiaryViewModel){

    var clickFlag by remember{ mutableStateOf(false) }
    var text by remember { mutableStateOf("Edit") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val indexOfFavorite = diaryViewModel.diaryList.indexOf(diary)
    val screenWidth = ((getScreenWidth(LocalContext.current).toDouble()) / 3).toInt()

    Box(
        modifier = Modifier
            .background(if (clickFlag) Color.White else Color.White)
            .clickable {
                clickFlag = !clickFlag
                if (clickFlag) {
                    keyboardController?.show()
                }
            }
            .width(screenWidth.dp)
            .padding(top = 3.dp, bottom = 3.dp)
    ){
        if(clickFlag){
            TextField(
                textStyle = TextStyle(fontSize = 15.sp),
                value = text,
                onValueChange = { newText ->
                    text = newText
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        clickFlag = !clickFlag
                        diaryViewModel.diaryList[indexOfFavorite].name = "#" + text
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray)
                    .heightIn(max = 50.dp)
            )
        }
        else{
            Text(
                fontSize = 15.sp,
                text = diary.name,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .widthIn(max = screenWidth.dp)
                    .heightIn(max = 20.dp)

            )
        }
    }
}