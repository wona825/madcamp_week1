package com.madcamp.phonebook.presentation.gallery.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.madcamp.phonebook.MainActivity.favorites

@Composable
fun Image_Tab(navController: NavHostController, favorite_list: MutableList<favorites>, delete_index: Int, favorite: favorites){

    Column() {
        // 1st Line
        Box(
            modifier = Modifier
                //.fillMaxWidth()
                .weight(1f)
                .border(2.dp, Color.Black)
        ){
            Row(){
                // Return Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            navController.navigate("Gallery_Tab")
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
                        .clickable{
                            //delete_index = favorite_list.indexOf(favorite)
                            navController.navigate("Gallery_Tab")
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

        }

        // 3rd Line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(2.dp, Color.Black)
        ){
            Row(){

                // Tags
                Box(
                    modifier = Modifier
                        .weight(7f)
                        .border(2.dp, Color.Black)
                ){

                }

                // Like or Not
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .border(2.dp, Color.Black)
                ){

                }
            }
        }

        // 4th Line, Description
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
                .border(2.dp, Color.Black)
        ){

        }
    }

}