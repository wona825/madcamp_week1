package com.madcamp.phonebook.presentation.Diary.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.madcamp.phonebook.R
import com.madcamp.phonebook.navigation.Screen
import com.madcamp.phonebook.presentation.gallery.favorites.favorites
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowIconForEachDay(week: Int, favoriteList: MutableList<favorites>, navController: NavController){

    // Get a current year and date.
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val formattedDate = currentDate.format(formatter)
    val formattedDateList = formattedDate.split(".")
    val currentYear = formattedDateList[0]
    val currentMonth = formattedDateList[1]
    val startDate = 7 * week + 1
    val screen = Screen()

    Row(
    ){
        for (i in 0 until 7) {
            val wantDate = if((startDate + i) < 10) currentYear + "." + currentMonth + ".0" + (startDate + i).toString() else currentYear + "." + currentMonth + "." + (startDate + i).toString()
            val favorite = favoriteList.filter {it.dateTime == wantDate}

            if (favorite.size != 0){
                val favorite_elem = favorite[0]
                val indexOfFavorite = favoriteList.indexOf(favorite_elem)
                Box(
                    modifier = Modifier.weight(1f)
                ){
                    when (favorite_elem.icon) {
                        1 -> Image(painter = painterResource(id = R.drawable.dog_1), contentDescription = null, modifier = Modifier.size(30.dp).clickable{navController.navigate(screen.ImageDetailScreen + "/${indexOfFavorite}")})
                        2 -> Image(painter = painterResource(id = R.drawable.dog_2), contentDescription = null, modifier = Modifier.size(30.dp).clickable{navController.navigate(screen.ImageDetailScreen + "/${indexOfFavorite}")})
                        3 -> Image(painter = painterResource(id = R.drawable.dog_3), contentDescription = null, modifier = Modifier.size(30.dp).clickable{navController.navigate(screen.ImageDetailScreen + "/${indexOfFavorite}")})
                        4 -> Image(painter = painterResource(id = R.drawable.dog_4), contentDescription = null, modifier = Modifier.size(30.dp).clickable{navController.navigate(screen.ImageDetailScreen + "/${indexOfFavorite}")})
                        5 -> Image(painter = painterResource(id = R.drawable.dog_5), contentDescription = null, modifier = Modifier.size(30.dp).clickable{navController.navigate(screen.ImageDetailScreen + "/${indexOfFavorite}")})
                        else -> {}
                    }
                }
            }
            else{
                Box(
                    modifier = Modifier.weight(1.5f)
                ){

                }
            }
        }
    }
}