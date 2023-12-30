@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.madcamp.phonebook

import android.Manifest
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class favorites (
    var name: String,
    val image: Uri?
)


val PERMISSION_READ_STORAGE = 111 // Permission for storage access.
val ACCESS_STORAGE = 501 // Request for access storage


// Form a grid to list the dogs_data
@Composable
fun Gallery_Frag(){
    FavoriteGrid()
}


// Making a grid composed of two image elements and spacers between elements (total 5 elements) in one row using LazyColumn.
@Composable
fun FavoriteGrid() {

    var getlist: MutableList<favorites> =  mutableListOf<favorites>()

    // context, states, and uri needed
    val curr_context = LocalContext.current
    var Perm_flag by remember{ mutableStateOf(false) } // Does permission flag is on or not?
    var Check_isGranted by remember{mutableStateOf(false)} // Does check is granted or not?
    var imageUri by remember {mutableStateOf<Uri?>(null)}
    var pre_checked by remember{ mutableStateOf(false)}  // Onclick makes authority check and make pre_checked.
    var add_image by remember{ mutableStateOf(false) }  // Onclick makes image accessing.

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) {
            uri: Uri? ->
        imageUri = uri
        imageUri?.let {
            getlist.add(favorites("A", imageUri))
        }
        add_image = false
    }

    val write_external_storage_launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
                isGranted -> Check_isGranted = isGranted
        }
    )

    // Lazy Column when zoom is not implemented. Otherwise, zoom out.
    LazyColumn(contentPadding = PaddingValues(0.dp, 8.dp)) {
            item {

                // Favorites List
                for (rowindex in getlist.indices step 2) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween // Arrange child elements with even space
                    ) {
                        // Elem1 (Spacer)
                        Spacer(
                            modifier = Modifier.width(10.dp)
                                .height(240.dp)
                        )
                        // Elem2 (Item)
                        FavoriteItem(
                            favorite = getlist[rowindex],
                            favorite_list = getlist
                        ) // Put odd ordered item first.
                        // Elem3 (Spacer)
                        Spacer(
                            modifier = Modifier.width(5.dp)
                                .height(240.dp)
                        )
                        // Elem4. Put even ordered item if exists.
                        if (rowindex + 1 < getlist.size) {
                            FavoriteItem(favorite = getlist[rowindex + 1], favorite_list = getlist)
                        } else {
                            Spacer(
                                modifier = Modifier.width(160.dp)
                                    .height(240.dp)
                            )
                        }
                        // Elem5. Spacer
                        Spacer(
                            modifier = Modifier.width(10.dp)
                                .height(240.dp)
                        )
                    }
                }

                // Button to add a new image.
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                    //.wrapContentSize(align = Alignment.Center),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    FilledTonalButton(

                        onClick = {
                            Perm_flag = true
                            add_image = true

                        },
                        modifier = Modifier.padding(8.dp)

                    ) {
                        Text("Add New Image")
                    }

                    if (Perm_flag && (!pre_checked)) {
                        write_external_storage_launcher.launch(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                        Perm_flag = false
                        pre_checked = true
                    }

                    if (Check_isGranted) {
                        if (add_image) {
                            SideEffect {
                                launcher.launch("image/*")
                            }
                        }
                    }
                }
            }
        }

}

// Show each item on the screen. Each item consists of image and the description. (2 elements)
@Composable
fun FavoriteItem(favorite: favorites, favorite_list: MutableList<favorites>){
    Column{
        // Elem1. image
        FavoriteImage(favorite = favorite, favorite_list = favorite_list)
        // Elem2. text
        Row(
            modifier = Modifier
                .padding(start = 8.dp)
        ){
            get_image_name(favorite, favorite_list)
        }
    }
}


// Show each image on the screen.
@Composable
fun FavoriteImage(favorite: favorites, favorite_list: MutableList<favorites>){

    val bitmap = remember { mutableStateOf<Bitmap?>(null)}
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
                        .padding(10.dp)
                        .width(150.dp)
                        .height(240.dp)
                )
        }
    }
}


// Get an image name from user.
@Composable
fun get_image_name(favorite: favorites, favorite_list: MutableList<favorites>){

    var Click_Flag by remember{ mutableStateOf(false) }
    var text by remember { mutableStateOf("Edit") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val index_getlist = favorite_list.indexOf(favorite)

    Box(
        modifier = Modifier
            .background(if(Click_Flag) Color.White else Color.White)
            .clickable {
                Click_Flag = !Click_Flag
                if(Click_Flag){
                    keyboardController?.show()
                }
            }
            .width(150.dp)
            .padding(10.dp)
    ){
        if(Click_Flag){
            TextField(
                textStyle = TextStyle(fontSize = 15.sp),
                value = text,
                onValueChange = { newText ->
                    text = newText
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = androidx.compose.ui.text.input.ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Handle the "Done" action
                        Click_Flag = !Click_Flag
                        favorite_list[index_getlist].name = text
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray)
            )
        }
        else{
            Text(
                fontSize = 15.sp,
                text = favorite.name,
                color = Color.Black,
                modifier = Modifier.padding(0.dp)

            )
        }
    }
}


