package com.madcamp.phonebook.presentation.gallery

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.madcamp.phonebook.presentation.gallery.favorites.favorites
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailScreen(navController: NavHostController, favorite_list: MutableList<favorites>, favorite: favorites){

    var clickFlagDescription by remember{ mutableStateOf(false) }
    var clickFlagName by remember{ mutableStateOf(false) }
    var description by remember { mutableStateOf("Write Description") }
    var name by remember { mutableStateOf("#Favorite") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val indexOfFavorite = favorite_list.indexOf(favorite)
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.padding(20.dp)
    ) {

        // 1st Line
        Box(
            modifier = Modifier
                .weight(1f)
        ){
            Row(){

                // Return Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            navController.popBackStack()
                        }
                ){
                    Icon(imageVector = Icons.Default.Close, contentDescription = null, modifier = Modifier
                        .fillMaxSize() // Adjust the size of the icon
                        .padding(end = 8.dp)  // Adjust the padding if needed
                        .align(Alignment.Center))
                }

                // Empty Box
                Box(
                    modifier = Modifier
                        .weight(6f)
                ){
                }

                // Trash Bin
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            favorite_list[indexOfFavorite].valid = false
                            navController.popBackStack()
                        }
                ){
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null, modifier = Modifier
                        .fillMaxSize() // Adjust the size of the icon
                        .padding(end = 8.dp)  // Adjust the padding if needed
                        .align(Alignment.Center))
                }
            }
        }

        // 2nd Line, Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(6f)
                .border(2.dp, Color.Black)
        ){

            val bitmap = remember { mutableStateOf<Bitmap?>(null) }
            val imageUri = favorite.image
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

                    )
                }
            }
        }

        // 3rd Line, Time and Place
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.8f)
                .background(Color.White)
                .align(Alignment.CenterHorizontally)
        ) {
            Row() {

                // dateTime
                Box(
                    modifier = Modifier.weight(5f),
                    contentAlignment = Alignment.CenterStart

                ){
                    favorite.dateTime?.let {

                        val dateAndTime = it.split(" ")
                        val timeData = dateAndTime[0].replace(":", "/")

                        Text(
                            fontSize = 20.sp,
                            text = timeData,
                            color = Color.Gray,
                            modifier = Modifier
                                .fillMaxSize()

                        )
                    }
                }
            }
        }


        // 4th Line
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .align(Alignment.CenterHorizontally)
        ){
            Row(){

                // Tags
                Box(
                    modifier = Modifier
                        .weight(7f)
                        .background(if (clickFlagName) Color.White else Color.White)
                        .clickable {
                            clickFlagName = !clickFlagName
                            if (clickFlagName) {
                                keyboardController?.show()
                            }
                        }
                        .padding(5.dp)
                ){
                    if(clickFlagName){
                        BasicTextField(
                            textStyle = TextStyle(fontSize = 15.sp),
                            value = name,
                            onValueChange = { newText ->
                                name = newText
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    clickFlagName = !clickFlagName
                                    favorite_list[indexOfFavorite].name = "#" + name
                                    keyboardController?.hide()
                                }
                            ),
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Transparent)
                                .heightIn(max = 50.dp),
                            decorationBox = { innerTextField ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(size = 16.dp)
                                        )
                                        .padding(all = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Create,
                                        contentDescription = "",
                                        tint = Color.DarkGray,
                                    )
                                    Spacer(modifier = Modifier.width(width = 8.dp))
                                    innerTextField()
                                }
                            }
                        )
                    }
                    else{
                        Text(
                            fontSize = 25.sp,
                            text = favorite.name,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxSize()

                        )
                    }
                }

                // Like or Not
                Box(
                    modifier = Modifier
                        .weight(1f)
                        //.border(2.dp, Color.Black)
                        .clickable {
                            favorite_list[indexOfFavorite].love = !(favorite.love)
                        }
                        .background(Color.White)
                ){
                    Icon(
                        imageVector = if (favorite.love) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize() // Adjust the size of the icon
                            .align(Alignment.Center)
                    )
                }
            }
        }

        // 5th Line, Description
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
                .background(if (clickFlagDescription) Color.White else Color.White)
                .clickable {
                    clickFlagDescription = !clickFlagDescription
                    if (clickFlagDescription) {
                        keyboardController?.show()
                    }
                }
                .padding(5.dp)
        ){
            if(clickFlagDescription){
                BasicTextField(
                    textStyle = TextStyle(fontSize = 15.sp),
                    value = description,
                    onValueChange = { newText ->
                        description = newText
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            clickFlagDescription = !clickFlagDescription
                            favorite_list[indexOfFavorite].description = description + "..."
                            keyboardController?.hide()
                        }
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .heightIn(max = 50.dp),
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(size = 16.dp)
                                )
                                .padding(all = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "",
                                tint = Color.DarkGray,
                            )
                            Spacer(modifier = Modifier.width(width = 8.dp))
                            innerTextField()
                        }
                    }
                )
            }
            else{
                Text(
                    fontSize = 15.sp,
                    text = favorite.description,
                    color = Color.Black,
                    modifier = Modifier.fillMaxSize()

                )
            }
        }

    }

}