package com.madcamp.phonebook.Gallery_Navigation.Gallery_Tab.Gallery_Screen

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.madcamp.phonebook.MainActivity

@RequiresApi(Build.VERSION_CODES.R)
fun getScreenWidth(context: Context): Int {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val displayMetrics = DisplayMetrics()
    context.display?.getRealMetrics(displayMetrics)
    return ((displayMetrics.widthPixels).toDouble() / 3).toInt()
}


// Show each item on the screen. Each item consists of image and the description. (2 elements)
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun FavoriteItem(navController: NavController, favorite: MainActivity.favorites, favorite_list: MutableList<MainActivity.favorites>){
    Column{
        // Elem1. image
        FavoriteImage(navController, favorite = favorite, favorite_list = favorite_list)
        // Elem2. text
        Row(){
            get_image_name(favorite, favorite_list)
        }
    }
}



