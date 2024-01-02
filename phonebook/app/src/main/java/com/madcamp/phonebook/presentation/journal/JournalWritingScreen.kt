package com.madcamp.phonebook.presentation.journal

import android.Manifest
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.madcamp.phonebook.R
import com.madcamp.phonebook.presentation.gallery.component.getScreenWidth
import com.madcamp.phonebook.presentation.gallery.favorites.favorites
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun JournalWritingScreen(navController: NavController, favoriteList: MutableList<favorites>){

    var permFlag by remember{ mutableStateOf(false) } // Does permission flag is on or not?
    var permIsGrantedFlag by remember{ mutableStateOf(false) } // Does check is granted or not?
    var permPrecheckedFlag by remember{ mutableStateOf(false) }  // Onclick makes authority check and make pre_checked.
    var storageAccessFlag by remember{ mutableStateOf(false) }  // Onclick makes image accessing.
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val keyboardController = LocalSoftwareKeyboardController.current

    var clickFlagName by remember{ mutableStateOf(false) }
    var contentName by remember { mutableStateOf("Write Title") }

    var clickFlagImage by remember{ mutableStateOf(false) }

    var clickFlagLike by remember{mutableStateOf(false)}

    var clickFlagDescription by remember{ mutableStateOf(false) }
    var contentDescription by remember { mutableStateOf("Write Description") }

    // Dropdown Bar
    var expanded by remember{ mutableStateOf(false)}
    val items = listOf("1", "2", "3", "4", "5")
    val icons = listOf(R.drawable.dog_1, R.drawable.dog_2, R.drawable.dog_3, R.drawable.dog_4, R.drawable.dog_5)
    var selectedIndex by remember {mutableStateOf(0)}
    var iconContent by remember{mutableStateOf(-1)}

    // Get a current year and date.
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val formattedDate = currentDate.format(formatter)
    val formattedDateList = formattedDate.split(".")
    val currentYear = formattedDateList[0]
    val currentMonth = formattedDateList[1]
    val currentDay = formattedDateList[2]

    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    val screenWidth = getScreenWidth(context)
    val iconSize = ((screenWidth.toDouble())/3).toInt()
    val imageSize = ((screenWidth.toDouble())/2).toInt()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) {
            uri: Uri? ->
        imageUri = uri
        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            }
            else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }
            clickFlagImage = true
        }

        storageAccessFlag = false
    }

    val write_external_storage_launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
                isGranted -> permIsGrantedFlag = isGranted
        }
    )

    Column(
        modifier = Modifier.padding(25.dp)
    ){

        // Date
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.5f)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                fontSize = 15.sp,
                text = formattedDate,
                color = Color.Black,
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center

            )
        }

        // Title
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (clickFlagName) Color.White else Color.White)
                .weight(1.5f)
                .clickable {
                    clickFlagName = !clickFlagName
                    if (clickFlagName) {
                        keyboardController?.show()
                    }
                }
                .padding(10.dp),
            contentAlignment = Alignment.Center,

        ){
            if(clickFlagName){
                BasicTextField(
                    textStyle = TextStyle(fontSize = 15.sp),
                    value = contentName,
                    onValueChange = { newText ->
                        contentName = newText
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            clickFlagName = !clickFlagName
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
                    text = contentName,
                    color = Color.Black,
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,

                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(9f)
                .padding(10.dp)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(30.dp, 30.dp)
            ) {

                item {
                    // Icon
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(iconSize.dp)
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ){
                        if(iconContent == -1){
                            Button(
                                onClick = { expanded = true }
                            ) {
                                Text(text = "Today I'm...")
                            }
                        }
                        else{
                            when (iconContent) {
                                1 -> Image(painter = painterResource(id = R.drawable.dog_1), contentDescription = null, modifier = Modifier.size(iconSize.dp).clickable{expanded = true})
                                2 -> Image(painter = painterResource(id = R.drawable.dog_2), contentDescription = null, modifier = Modifier.size(iconSize.dp).clickable{expanded = true})
                                3 -> Image(painter = painterResource(id = R.drawable.dog_3), contentDescription = null, modifier = Modifier.size(iconSize.dp).clickable{expanded = true})
                                4 -> Image(painter = painterResource(id = R.drawable.dog_4), contentDescription = null, modifier = Modifier.size(iconSize.dp).clickable{expanded = true})
                                5 -> Image(painter = painterResource(id = R.drawable.dog_5), contentDescription = null, modifier = Modifier.size(iconSize.dp).clickable{expanded = true})
                                else -> {}
                            }
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            items.forEachIndexed { index, s ->
                                androidx.compose.material.DropdownMenuItem(onClick = {
                                    selectedIndex = index
                                    expanded = false
                                }) {
                                    when (s) {
                                        "1" -> Image(painter = painterResource(id = R.drawable.dog_1), contentDescription = null, modifier = Modifier.size(30.dp))
                                        "2" -> Image(painter = painterResource(id = R.drawable.dog_2), contentDescription = null, modifier = Modifier.size(30.dp))
                                        "3" -> Image(painter = painterResource(id = R.drawable.dog_3), contentDescription = null, modifier = Modifier.size(30.dp))
                                        "4" -> Image(painter = painterResource(id = R.drawable.dog_4), contentDescription = null, modifier = Modifier.size(30.dp))
                                        "5" -> Image(painter = painterResource(id = R.drawable.dog_5), contentDescription = null, modifier = Modifier.size(30.dp))
                                        else -> {}
                                    }
                                    iconContent = selectedIndex + 1
                                }
                            }
                        }
                        }
                    }
                }

                item {
                    // Image
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(imageSize.dp)
                            .clickable {
                                permFlag = true
                                storageAccessFlag = true
                            }
                    ) {
                        if(clickFlagImage){
                            bitmap.value?.let {btm ->
                                Image(
                                    bitmap = btm.asImageBitmap(),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .width(imageSize.dp)
                                        .height(imageSize.dp)
                                )
                            }
                        }
                        else{
                            FilledTonalButton(

                                onClick = {
                                    permFlag = true
                                    storageAccessFlag = true
                                },
                                modifier = Modifier.align(Alignment.Center)

                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "",
                                )
                            }
                        }
                        if (permFlag && (!permPrecheckedFlag)) {
                            write_external_storage_launcher.launch(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            )
                            permFlag = false
                            permPrecheckedFlag = true
                        }

                        if (permIsGrantedFlag) {
                            if (storageAccessFlag) {
                                SideEffect {
                                    launcher.launch("image/*")
                                }
                            }
                        }
                    }
                }

                item {
                    // Tag, Love
                    Box() {
                        Row(
                            modifier = Modifier.fillMaxSize()
                        ) {

                            // Tag
                            Box(
                                modifier = Modifier.weight(6f)
                            ) {

                            }

                            // Love
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        clickFlagLike = !clickFlagLike
                                    }

                            ) {
                                Icon(
                                    imageVector = if(clickFlagLike) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize() // Adjust the size of the icon
                                        .align(Alignment.Center)
                                )
                            }

                        }
                    }
                }

                item {
                    //Description
                    // Title
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (clickFlagDescription) Color.White else Color.White)
                            .clickable {
                                clickFlagDescription = !clickFlagDescription
                                if (clickFlagDescription) {
                                    keyboardController?.show()
                                }
                            }
                            .padding(10.dp),
                    ) {
                        if (clickFlagDescription) {
                            BasicTextField(
                                textStyle = TextStyle(fontSize = 15.sp),
                                value = contentDescription,
                                onValueChange = { newText ->
                                    contentDescription = newText
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        clickFlagDescription = !clickFlagDescription
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
                        } else {
                            Text(
                                fontSize = 15.sp,
                                text = contentDescription,
                                color = Color.Black,
                                modifier = Modifier.fillMaxSize()

                            )
                        }
                    }
                }
            }
        }

        // Okay Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.5f)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            FilledTonalButton(
                onClick = {
                    //favoriteViewModel.addFavorite(Favorites(0, contentName, imageUri, clickFlagLike, contentDescription, true, formattedDate))
                    favoriteList.add(favorites(contentName, iconContent, imageUri, clickFlagLike, contentDescription, true, formattedDate))
                    navController.popBackStack()
                }
            ) {
                Text("Add to My Journal")
            }

        }


    }
}
