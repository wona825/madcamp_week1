package com.madcamp.phonebook.Gallery_Navigation.Gallery_Tab.Gallery_Screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.madcamp.phonebook.MainActivity

@Composable
fun Show_Gallery_Image(navController: NavController, favoritelist: MutableList<MainActivity.favorites>){
    for (index in favoritelist.indices step 3) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            //horizontalArrangement = Arrangement.SpaceBetween // Arrange child elements with even space
        ) {

            // Elem1 (Item)
            FavoriteItem(
                navController,
                favorite = favoritelist[index],
                favorite_list = favoritelist
            )

            // Elem2. Second Item
            if ((index + 1) == favoritelist.size) {
              // do nothing

            } else if ((index + 2) == favoritelist.size) {
                FavoriteItem(navController, favorite = favoritelist[index + 1], favorite_list = favoritelist)
            } else {
                FavoriteItem(navController, favorite = favoritelist[index + 1], favorite_list = favoritelist)
                FavoriteItem(navController, favorite = favoritelist[index + 2], favorite_list = favoritelist)
            }

        }
    }
}