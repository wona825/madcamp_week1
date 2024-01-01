package com.madcamp.phonebook.Gallery_Navigation.Gallery_Tab.Gallery_Screen

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.madcamp.phonebook.MainActivity


// Get an image name from user.
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun get_image_name(favorite: MainActivity.favorites, favorite_list: MutableList<MainActivity.favorites>){

    var Click_Flag by remember{ mutableStateOf(false) }
    var text by remember { mutableStateOf("Edit") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val index_getlist = favorite_list.indexOf(favorite)
    val screen_width = ((getScreenWidth(LocalContext.current).toDouble()) / 3).toInt()

    Box(
        modifier = Modifier
            .background(if (Click_Flag) Color.White else Color.White)
            .clickable {
                Click_Flag = !Click_Flag
                if (Click_Flag) {
                    keyboardController?.show()
                }
            }
            .width(screen_width.dp)
            .padding(top = 3.dp, bottom = 3.dp)
    ){
        if(Click_Flag){
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
                        // Handle the "Done" action
                        Click_Flag = !Click_Flag
                        favorite_list[index_getlist].name = "#" + text
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
                text = favorite.name,
                color = Color.Black,
                modifier = Modifier
                    .widthIn(max = screen_width.dp)
                    .heightIn(max = 20.dp)

            )
        }
    }
}