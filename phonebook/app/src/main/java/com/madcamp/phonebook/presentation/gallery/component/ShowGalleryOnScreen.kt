package com.madcamp.phonebook.presentation.gallery.component

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.madcamp.phonebook.MainActivity
import com.madcamp.phonebook.presentation.gallery.favorites.favorites


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun ShowGalleryOnScreen(navController: NavController, favoritelist: MutableList<favorites>){

//        var localFavoriteList: MutableList<Favorites> =  mutableListOf<Favorites>()
//
//        favoriteViewModel.getAllFavorites{
//            favorites ->
//            favorites.forEach{
//                favorite -> localFavoriteList.add(favorite)
//            }
//        }
//
//        val size = localFavoriteList.size

        //Log.d("The number", "Size is $size")

        for (index in favoritelist.indices step 3) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                //horizontalArrangement = Arrangement.SpaceBetween // Arrange child elements with even space
            ) {

                favoritelist.removeAll { it.valid == false } // Remove all invalid data.

                // Elem1 (Item)
                ShowItemOnScreen(
                    navController,
                    favorite = favoritelist[index],
                    favorite_list = favoritelist
                )

                // Elem2. Second Item
                if ((index + 1) == favoritelist.size) {
                    // do nothing

                } else if ((index + 2) == favoritelist.size) {
                    ShowItemOnScreen(
                        navController,
                        favorite = favoritelist[index + 1],
                        favorite_list = favoritelist
                    )
                } else {
                    ShowItemOnScreen(
                        navController,
                        favorite = favoritelist[index + 1],
                        favorite_list = favoritelist
                    )
                    ShowItemOnScreen(
                        navController,
                        favorite = favoritelist[index + 2],
                        favorite_list = favoritelist
                    )
                }


        }
    }
}


// Show each item on the screen. Each item consists of image and the description. (2 elements)
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun ShowItemOnScreen(navController: NavController, favorite: favorites, favorite_list: MutableList<favorites>){

    val screenWidth = (getScreenWidth(LocalContext.current).toDouble() / 3).toInt()

    Column{
        // Elem1. image
        ShowImage(navController, favorite = favorite, favorite_list = favorite_list)
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
                    ShowImageInformation(favorite, favorite_list)
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
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
    }
}


@RequiresApi(Build.VERSION_CODES.R)
fun getScreenWidth(context: Context): Int {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val displayMetrics = DisplayMetrics()
    context.display?.getRealMetrics(displayMetrics)
    return ((displayMetrics.widthPixels).toDouble() / 3).toInt()
}


