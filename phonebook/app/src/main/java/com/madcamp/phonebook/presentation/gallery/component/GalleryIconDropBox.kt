package com.madcamp.phonebook.presentation.gallery.component


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.madcamp.phonebook.R

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun GalleryIconDropBox(iconValue: MutableState<Int>){

    var expanded by remember{ mutableStateOf(false)}
    val items = listOf("1", "2", "3", "4", "5")
    val screenWidth = getScreenWidth(LocalContext.current)
    val iconSize = screenWidth / 8
    var selectedIndex by remember {mutableStateOf(0)}

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ){

            when (iconValue.value) {
                1 -> Image(painter = painterResource(id = R.drawable.dog_1), contentDescription = null, modifier = Modifier.size(iconSize.dp).clickable{expanded = true})
                2 -> Image(painter = painterResource(id = R.drawable.dog_2), contentDescription = null, modifier = Modifier.size(iconSize.dp).clickable{expanded = true})
                3 -> Image(painter = painterResource(id = R.drawable.dog_3), contentDescription = null, modifier = Modifier.size(iconSize.dp).clickable{expanded = true})
                4 -> Image(painter = painterResource(id = R.drawable.dog_4), contentDescription = null, modifier = Modifier.size(iconSize.dp).clickable{expanded = true})
                5 -> Image(painter = painterResource(id = R.drawable.dog_5), contentDescription = null, modifier = Modifier.size(iconSize.dp).clickable{expanded = true})
                else -> {}
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
                    iconValue.value = selectedIndex + 1

                }
            }
        }
    }
}