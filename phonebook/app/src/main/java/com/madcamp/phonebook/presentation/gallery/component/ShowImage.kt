package com.madcamp.phonebook.presentation.gallery.component

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.madcamp.phonebook.MainActivity
import com.madcamp.phonebook.navigation.Screen

// Show each image on the screen.
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun ShowImage(navController:NavController, favorite: MainActivity.favorites, favorite_list: MutableList<MainActivity.favorites>){

    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val imageUri = favorite.image
    val context = LocalContext.current
    val screen = Screen()
    val screenWidth = ((getScreenWidth(LocalContext.current).toDouble()) / 3).toInt()

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
                    .width(screenWidth.dp)
                    .height(screenWidth.dp)
                    .clickable {
                        val this_index = favorite_list.indexOf(favorite)
                        navController.navigate(screen.ImageDetailScreen + "/${this_index}")
                    }

            )
        }
    }
}
