package com.madcamp.phonebook.presentation.gallery

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.room.Room
import com.madcamp.phonebook.presentation.gallery.component.SearchImage
import com.madcamp.phonebook.presentation.gallery.component.ShowGalleryOnScreen
import com.madcamp.phonebook.domain.model.Contact
import com.madcamp.phonebook.presentation.database.AppDatabase
import com.madcamp.phonebook.presentation.database.FavoriteViewModel
import com.madcamp.phonebook.presentation.database.Favorites
import com.madcamp.phonebook.presentation.gallery.favorites.favorites

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun GalleryScreen(navController: NavController, favoritelist: MutableList<favorites>, favoriteViewModel: FavoriteViewModel){

    var permFlag by remember{ mutableStateOf(false) } // Does permission flag is on or not?
    var permIsGrantedFlag by remember{ mutableStateOf(false) } // Does check is granted or not?
    var permPrecheckedFlag by remember{ mutableStateOf(false) }  // Onclick makes authority check and make pre_checked.
    var storageAccessFlag by remember{ mutableStateOf(false) }  // Onclick makes image accessing.
    var imageUri by remember { mutableStateOf<Uri?>(null) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ){
        SearchImage(favoritelist = favoritelist)


        Box(
            modifier = Modifier.weight(8f)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(0.dp, 0.dp)
            ) {

                item {

                    ShowGalleryOnScreen(navController, favoritelist = favoritelist, favoriteViewModel)

                }
            }
        }



        }
    }