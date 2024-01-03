package com.madcamp.phonebook.presentation.gallery

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.madcamp.phonebook.R
import com.madcamp.phonebook.domain.model.Diary
import com.madcamp.phonebook.presentation.diary.viewmodel.DiaryViewModel
import com.madcamp.phonebook.presentation.component.TextBox
import com.madcamp.phonebook.presentation.gallery.component.GalleryIconDropBox
import com.madcamp.phonebook.presentation.gallery.component.getScreenWidth
import com.madcamp.phonebook.ui.theme.Brown200
import com.madcamp.phonebook.ui.theme.Brown400
import com.madcamp.phonebook.ui.theme.Gray100

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailScreen(navController: NavHostController, diaryViewModel: DiaryViewModel, diary: Diary){

    var clickFlagDescription by remember{ mutableStateOf(false) }
    var clickFlagName by remember{ mutableStateOf(false) }
    var description by remember { mutableStateOf("Write Description") }
    var name by remember { mutableStateOf("#Favorite") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val indexOfDiary = diaryViewModel.diaryList.indexOf(diary)
    val screenWidth = getScreenWidth(LocalContext.current)
    val iconWidth = screenWidth / 2

    var isEditMode by remember { mutableStateOf(false) }
    var diaryLikeStatus by remember { mutableStateOf(diaryViewModel.diaryList[indexOfDiary].favoriteStatus) }
    var diaryNameStatus by remember { mutableStateOf(diaryViewModel.diaryList[indexOfDiary].name) }
    var diaryDescriptionStatus by remember { mutableStateOf(diaryViewModel.diaryList[indexOfDiary].description) }
    var diaryIconStatus by remember { mutableStateOf(diaryViewModel.diaryList[indexOfDiary].icon) }
    val scrollState =  rememberScrollState()

        Column(
            modifier = Modifier
                .background(color = Color(0xFFC4BDAC))
                .padding(horizontal = 20.dp, vertical = 50.dp)
                .fillMaxSize()
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    androidx.compose.material.Icon(
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
            }
            Box(modifier = Modifier.padding(20.dp).fillMaxWidth().weight(6f)) {
                Column(modifier = Modifier.verticalScroll(scrollState)) {
                    Box(
                        modifier = Modifier
                            .size(width = iconWidth.dp, height = iconWidth.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (!isEditMode) {
                            when (diaryIconStatus) {
                                1 -> Image(
                                    painter = painterResource(id = R.drawable.dog_1),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )

                                2 -> Image(
                                    painter = painterResource(id = R.drawable.dog_2),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )

                                3 -> Image(
                                    painter = painterResource(id = R.drawable.dog_3),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )

                                4 -> Image(
                                    painter = painterResource(id = R.drawable.dog_4),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )

                                5 -> Image(
                                    painter = painterResource(id = R.drawable.dog_5),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )

                                else -> {}
                            }
                        } else {
                            GalleryIconDropBox(iconValue = mutableStateOf(diaryIconStatus))
                        }
                    }

                    Box() {
                        if (!isEditMode) {
                            Text(
                                text = diaryNameStatus,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily.SansSerif,
                                color = Brown400
                            )
                        } else {
                            androidx.compose.material.Text(
                                text = "* 제목",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily.SansSerif,
                                color = Brown400
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            TextBox(
                                type = "Title",
                                text = diaryNameStatus,
                                width = 320.dp,
                                height = 65.dp,
                                boxColor = Gray100,
                                readOnly = !isEditMode,
                                onValueChange = { newText ->
                                    diaryNameStatus = newText
                                    diaryViewModel.diaryList[indexOfDiary].name = diaryNameStatus
                                }
                            )
                        }
                    }

                    Box() {
                        if (!isEditMode) {
                            Text(
                                text = diaryDescriptionStatus,
                                fontSize = 19.sp,
                                fontFamily = FontFamily.SansSerif,
                                color = Brown400
                            )
                        } else {
                            androidx.compose.material.Text(
                                text = "* 내용",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily.SansSerif,
                                color = Brown400
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            TextBox(
                                type = "Title",
                                text = diaryNameStatus,
                                width = 320.dp,
                                height = 200.dp,
                                boxColor = Gray100,
                                readOnly = !isEditMode,
                                onValueChange = { newText ->
                                    diaryDescriptionStatus = newText
                                    diaryViewModel.diaryList[indexOfDiary].description =
                                        diaryNameStatus
                                }
                            )
                        }
                    }

                }
            }

            Box(modifier = Modifier.weight(1f)){
                if (!isEditMode) {
                    Box(
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .fillMaxWidth()
                            .height(55.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Brown400)
                            .clickable { isEditMode = !isEditMode }
                            .border(1.dp, Color.Transparent, RoundedCornerShape(4))
                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.material.Icon(
                            imageVector = Icons.Filled.Edit,
                            modifier = Modifier
                                .size(30.dp)
                            ,
                            contentDescription = "Edit",
                            tint = Brown200
                        )
                    }

                }
                else{
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .size(130.dp, 55.dp)
                                .clip(RoundedCornerShape(50))
                                .background(Brown400),
                            contentAlignment = Alignment.Center
                        ) {
                            androidx.compose.material.Icon(
                                imageVector = Icons.Filled.Add,
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable { isEditMode = !isEditMode },
                                contentDescription = "Finish",
                                tint = Brown200
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(130.dp, 55.dp)
                                .clip(RoundedCornerShape(50))
                                .background(Brown400),
                            contentAlignment = Alignment.Center
                        ) {
                            androidx.compose.material.Icon(
                                imageVector = Icons.Filled.Delete,
                                modifier = Modifier.size(30.dp),
                                contentDescription = "message_button",
                                tint = Brown200
                            )
                        }
                    }
                }

            }

                    }
        }

        // 2nd Line, Image
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(6f)
//                .border(2.dp, Color.Black)
//        ){
//
//            val bitmap = remember { mutableStateOf<Bitmap?>(null) }
//            val imageUri = diary.image
//            val context = LocalContext.current
//
//            imageUri?.let {
//                if (Build.VERSION.SDK_INT < 28) {
//                    bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
//                }
//                else {
//                    val source = ImageDecoder.createSource(context.contentResolver, it)
//                    bitmap.value = ImageDecoder.decodeBitmap(source)
//                }
//                bitmap.value?.let {btm ->
//                    Image(
//                        bitmap = btm.asImageBitmap(),
//                        contentDescription = null,
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .fillMaxSize()
//                    )
//                }
//            }
//        }
//
//        // 3rd Line, Time and Place
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .weight(0.8f)
//                .background(Color.White)
//                .align(Alignment.CenterHorizontally)
//        ) {
//            Row() {
//
//                // dateTime
//                Box(
//                    modifier = Modifier.weight(5f),
//                    contentAlignment = Alignment.CenterStart
//
//                ){
//                    diary.dateTime?.let {
//
//                        val dateAndTime = it.split(" ")
//                        val timeData = dateAndTime[0].replace(":", "/")
//
//                        Text(
//                            fontSize = 20.sp,
//                            text = timeData,
//                            color = Color.Gray,
//                            modifier = Modifier
//                                .fillMaxSize()
//
//                        )
//                    }
//                }
//            }
//        }
//
//
//        // 4th Line
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .weight(1f)
//                .align(Alignment.CenterHorizontally)
//        ){
//            Row(){
//
//                // Tags
//                Box(
//                    modifier = Modifier
//                        .weight(7f)
//                        .background(if (clickFlagName) Color.White else Color.White)
//                        .clickable {
//                            clickFlagName = !clickFlagName
//                            if (clickFlagName) {
//                                keyboardController?.show()
//                            }
//                        }
//                        .padding(5.dp)
//                ){
//                    if(clickFlagName){
//                        BasicTextField(
//                            textStyle = TextStyle(fontSize = 15.sp),
//                            value = name,
//                            onValueChange = { newText ->
//                                name = newText
//                            },
//                            keyboardOptions = KeyboardOptions.Default.copy(
//                                imeAction = ImeAction.Done
//                            ),
//                            keyboardActions = KeyboardActions(
//                                onDone = {
//                                    clickFlagName = !clickFlagName
//                                    diaryViewModel.diaryList[indexOfDiary].name = "#" + name
//                                    keyboardController?.hide()
//                                }
//                            ),
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .background(Color.Transparent)
//                                .heightIn(max = 50.dp),
//                            decorationBox = { innerTextField ->
//                                Row(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .background(
//                                            color = Color.White,
//                                            shape = RoundedCornerShape(size = 16.dp)
//                                        )
//                                        .padding(all = 5.dp),
//                                    verticalAlignment = Alignment.CenterVertically,
//                                ) {
//                                    Icon(
//                                        imageVector = Icons.Default.Create,
//                                        contentDescription = "",
//                                        tint = Color.DarkGray,
//                                    )
//                                    Spacer(modifier = Modifier.width(width = 8.dp))
//                                    innerTextField()
//                                }
//                            }
//                        )
//                    }
//                    else{
//                        Text(
//                            fontSize = 25.sp,
//                            text = diary.name,
//                            color = Color.Black,
//                            fontWeight = FontWeight.Bold,
//                            modifier = Modifier
//                                .fillMaxSize()
//
//                        )
//                    }
//                }
//
//                // Like or Not
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        //.border(2.dp, Color.Black)
//                        .clickable {
//                            diaryViewModel.diaryList[indexOfDiary].favoriteStatus = !(diary.favoriteStatus)
//                        }
//                        .background(Color.White)
//                ){
//                    Icon(
//                        imageVector = if (diary.favoriteStatus) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
//                        contentDescription = null,
//                        modifier = Modifier
//                            .fillMaxSize() // Adjust the size of the icon
//                            .align(Alignment.Center)
//                    )
//                }
//            }
//        }
//
//        // 5th Line, Description
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(4f)
//                .background(if (clickFlagDescription) Color.White else Color.White)
//                .clickable {
//                    clickFlagDescription = !clickFlagDescription
//                    if (clickFlagDescription) {
//                        keyboardController?.show()
//                    }
//                }
//                .padding(5.dp)
//        ){
//            if(clickFlagDescription){
//                BasicTextField(
//                    textStyle = TextStyle(fontSize = 15.sp),
//                    value = description,
//                    onValueChange = { newText ->
//                        description = newText
//                    },
//                    keyboardOptions = KeyboardOptions.Default.copy(
//                        imeAction = ImeAction.Done
//                    ),
//                    keyboardActions = KeyboardActions(
//                        onDone = {
//                            clickFlagDescription = !clickFlagDescription
//                            diaryViewModel.diaryList[indexOfDiary].description = description + "..."
//                            keyboardController?.hide()
//                        }
//                    ),
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color.Transparent)
//                        .heightIn(max = 50.dp),
//                    decorationBox = { innerTextField ->
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .background(
//                                    color = Color.White,
//                                    shape = RoundedCornerShape(size = 16.dp)
//                                )
//                                .padding(all = 5.dp),
//                            verticalAlignment = Alignment.CenterVertically,
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.Create,
//                                contentDescription = "",
//                                tint = Color.DarkGray,
//                            )
//                            Spacer(modifier = Modifier.width(width = 8.dp))
//                            innerTextField()
//                        }
//                    }
//                )
//            }
//            else{
//                Text(
//                    fontSize = 15.sp,
//                    text = diary.description,
//                    color = Color.Black,
//                    modifier = Modifier.fillMaxSize()
//
//                )
//            }
//        }

